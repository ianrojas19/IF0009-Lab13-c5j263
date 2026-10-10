package com.medpharm.cr.medpharm_backend.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

@Entity
public class RecetaMedica {

    static final String[] allowedStates = { "PENDIENTE", "DESPACHADA", "CANCELADA" };

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String codigoReceta;

    @Column(nullable = false)
    private String pacienteNombre;

    // Relación Muchos a Uno: Muchas recetas pueden pertenecer a un mismo Médico
    // (Usuario)
    @ManyToOne
    @JoinColumn(name = "medico_id", nullable = false)
    private Usuario medico;

    @Column(nullable = false)
    private String estado; // PENDIENTE, DESPACHADA, CANCELADA

    @Column(nullable = false)
    private LocalDateTime fechaEmision;

    @OneToMany(mappedBy = "receta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleReceta> detalles = new ArrayList<>();

    public RecetaMedica() {
        this.fechaEmision = LocalDateTime.now();
        this.estado = "PENDIENTE";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoReceta() {
        return codigoReceta;
    }

    public void setCodigoReceta(String codigoReceta) {
        this.codigoReceta = codigoReceta;
    }

    public String getPacienteNombre() {
        return pacienteNombre;
    }

    public void setPacienteNombre(String pacienteNombre) {
        this.pacienteNombre = pacienteNombre;
    }

    public Usuario getMedico() {
        return medico;
    }

    public void setMedico(Usuario medico) {
        this.medico = medico;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        // Validación rápida para los estados permitidos
        if (estado.trim() == "" || estado == null || !Arrays.asList(allowedStates).contains(estado.toUpperCase()))
            throw new IllegalArgumentException("El estado ingresado no es valido para la receta");

        this.estado = estado;
    }

    public LocalDateTime getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDateTime fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public List<DetalleReceta> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleReceta> detalles) {
        this.detalles = detalles;
    }
}
