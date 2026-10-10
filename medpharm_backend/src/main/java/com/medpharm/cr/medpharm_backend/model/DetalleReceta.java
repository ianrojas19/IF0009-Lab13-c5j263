package com.medpharm.cr.medpharm_backend.model;

import jakarta.persistence.*;

@Entity
public class DetalleReceta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación Muchos a Uno: Muchos detalles (renglones) pertenecen a una misma
    // Receta
    @ManyToOne
    @JoinColumn(name = "receta_id", nullable = false)
    private RecetaMedica receta;

    // Relación Muchos a Uno: Un detalle hace referencia a un Medicamento del
    // catálogo
    @ManyToOne
    @JoinColumn(name = "medicamento_id", nullable = false)
    private Medicamento medicamento;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false)
    private String dosisIndicada;

    public DetalleReceta() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RecetaMedica getReceta() {
        return receta;
    }

    public void setReceta(RecetaMedica receta) {
        this.receta = receta;
    }

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getDosisIndicada() {
        return dosisIndicada;
    }

    public void setDosisIndicada(String dosisIndicada) {
        this.dosisIndicada = dosisIndicada;
    }
}
