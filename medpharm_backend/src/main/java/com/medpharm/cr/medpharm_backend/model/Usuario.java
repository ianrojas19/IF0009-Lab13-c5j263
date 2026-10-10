package com.medpharm.cr.medpharm_backend.model;

import java.util.Arrays;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Usuario {

    static final String[] allowedRoles = { "MEDICO", "FARMACEUTICO" };

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "nombre_completo", nullable = false)
    private String nombreCompleto;

    @Column(nullable = false)
    private String rol; // MEDICO, FARMACEUTICO

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getRol() {
        return rol;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        if (username == null || username.trim() == "")
            throw new IllegalArgumentException("El nombre de usuario es requerido");
        this.username = username;
    }

    public void setPassword(String password) {
        if (password == null || password.trim() == "")
            throw new IllegalArgumentException("La contraseña es requerida");
        this.password = password;
    }

    public void setNombreCompleto(String nombreCompleto) {
        if (nombreCompleto == null || nombreCompleto.trim() == "")
            throw new IllegalArgumentException("El nombre es requerido");

        this.nombreCompleto = nombreCompleto;
    }

    public void setRol(String rol) {
        if (rol == null || !Arrays.asList(allowedRoles).contains(rol.toUpperCase()))
            throw new IllegalArgumentException("El rol ingresado no es valido para el usuario");

        this.rol = rol;
    }

}
