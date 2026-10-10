package com.medpharm.cr.medpharm_backend.security.dto;

public record AuthResponseDTO(String token, String username, String rol) {

    public AuthResponseDTO {
        if (token == null || token.trim() == "")
            throw new IllegalArgumentException("El token es requerido");

        if (username == null || username.trim() == "")
            throw new IllegalArgumentException("El username es requerido");

        if (rol == null || rol.trim() == "")
            throw new IllegalArgumentException("El rol es requerido");
    }
}
