package com.medpharm.cr.medpharm_backend.service;

import com.medpharm.cr.medpharm_backend.model.DetalleReceta;
import com.medpharm.cr.medpharm_backend.model.Medicamento;
import com.medpharm.cr.medpharm_backend.model.RecetaMedica;
import com.medpharm.cr.medpharm_backend.model.Usuario;
import com.medpharm.cr.medpharm_backend.repository.MedicamentoRepository;
import com.medpharm.cr.medpharm_backend.repository.RecetaMedicaRepository;
import com.medpharm.cr.medpharm_backend.repository.UsuarioRepository;
import com.medpharm.cr.medpharm_backend.security.JwtUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RecetaService {

    private final RecetaMedicaRepository recetaRepository;
    private final MedicamentoRepository medicamentoRepository;
    private final UsuarioRepository usuarioRepository;
    private final JwtUtils jwtUtils;

    public RecetaService(RecetaMedicaRepository recetaRepository, MedicamentoRepository medicamentoRepository,
            UsuarioRepository usuarioRepository, JwtUtils jwtUtils) {
        this.recetaRepository = recetaRepository;
        this.medicamentoRepository = medicamentoRepository;
        this.usuarioRepository = usuarioRepository;
        this.jwtUtils = jwtUtils;
    }

    public List<RecetaMedica> obtenerTodas() {
        return recetaRepository.findAll();
    }

    public List<RecetaMedica> obtenerPorEstado(String estado) {
        return recetaRepository.findByEstado(estado.toUpperCase());
    }

    @Transactional
    public RecetaMedica registrarReceta(RecetaMedica receta, String authHeader) {

        String token = authHeader.substring(7);
        String username = jwtUtils.getUserNameFromJwtToken(token);
        Usuario medico = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Médico no encontrado"));

        receta.setMedico(medico);
        if (receta.getCodigoReceta() == null || receta.getCodigoReceta().isBlank()) {
            receta.setCodigoReceta("REC-" + System.currentTimeMillis());
        }
        if (receta.getEstado() == null || receta.getEstado().isBlank()) {
            receta.setEstado("PENDIENTE");
        }
        if (receta.getFechaEmision() == null) {
            receta.setFechaEmision(java.time.LocalDateTime.now());
        }

        for (DetalleReceta detalle : receta.getDetalles()) {
            Medicamento medicamentoDB = medicamentoRepository.findById(detalle.getMedicamento().getId())
                    .orElseThrow(() -> new RuntimeException("Medicamento no encontrado en el sistema"));
            if (medicamentoDB.getStock() < detalle.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para: " + medicamentoDB.getNombre());
            }

            // Descontamos del inventario y actualizamos
            medicamentoDB.setStock(medicamentoDB.getStock() - detalle.getCantidad());
            medicamentoRepository.save(medicamentoDB);

            detalle.setMedicamento(medicamentoDB);
            detalle.setReceta(receta);
        }

        return recetaRepository.save(receta);
    }

    public RecetaMedica cambiarEstado(Long id, String nuevoEstado) {
        RecetaMedica receta = recetaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Receta no encontrada"));

        receta.setEstado(nuevoEstado);
        return recetaRepository.save(receta);
    }
}
