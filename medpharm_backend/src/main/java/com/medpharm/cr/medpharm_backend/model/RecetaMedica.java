package com.medpharm.cr.medpharm_backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Entity
public class RecetaMedica {

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

    // Relación Uno a Muchos: Una receta tiene muchos renglones (Detalles)
    // El "mappedBy" le dice a JPA que la relación ya está definida en DetalleReceta
    // por la variable "receta"
    @OneToMany(mappedBy = "receta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleReceta> detalles = new ArrayList<>();

    public RecetaMedica() {
        // Inicializamos la fecha y el estado por defecto al crear una receta nueva
        this.fechaEmision = LocalDateTime.now();
        this.estado = "PENDIENTE";
    }

    // --- GETTERS Y SETTERS ---

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
        if (estado != null
                && (estado.equals("PENDIENTE") || estado.equals("DESPACHADA") || estado.equals("CANCELADA"))) {
            this.estado = estado;
        } else {
            throw new IllegalArgumentException("Estado inválido para la receta");
        }
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
