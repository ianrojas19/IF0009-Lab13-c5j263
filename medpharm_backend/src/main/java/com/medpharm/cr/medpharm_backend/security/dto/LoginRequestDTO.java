package com.medpharm.cr.medpharm_backend.security.dto;

public record LoginRequestDTO(String username, String password) {
    public LoginRequestDTO {
        if (username == null || username.trim() == "")
            throw new IllegalArgumentException("El username es requerido");

        if (password == null || password.trim() == "")
            throw new IllegalArgumentException("El password es requerido");
    }
}
