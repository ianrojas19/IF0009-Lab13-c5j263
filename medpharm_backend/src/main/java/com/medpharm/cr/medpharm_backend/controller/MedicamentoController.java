package com.medpharm.cr.medpharm_backend.controller;

import com.medpharm.cr.medpharm_backend.model.Medicamento;
import com.medpharm.cr.medpharm_backend.repository.MedicamentoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/medicamentos")
@CrossOrigin(origins = "*")
public class MedicamentoController {

    private final MedicamentoRepository medicamentoRepository;

    public MedicamentoController(MedicamentoRepository medicamentoRepository) {
        this.medicamentoRepository = medicamentoRepository;
    }

    @GetMapping
    public ResponseEntity<List<Medicamento>> obtenerTodos() {
        return ResponseEntity.ok(medicamentoRepository.findAll());
    }
}
