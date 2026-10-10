package com.medpharm.cr.medpharm_backend.controller;

import com.medpharm.cr.medpharm_backend.model.Usuario;
import com.medpharm.cr.medpharm_backend.repository.UsuarioRepository;
import com.medpharm.cr.medpharm_backend.security.JwtUtils;
import com.medpharm.cr.medpharm_backend.security.dto.AuthResponseDTO;
import com.medpharm.cr.medpharm_backend.security.dto.LoginRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    private final UsuarioRepository usuarioRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UsuarioRepository usuarioRepository, JwtUtils jwtUtils, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequestDTO loginRequest) {

        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(loginRequest.username());

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();

            if (passwordEncoder.matches(loginRequest.password(), usuario.getPassword())) {
                String jwt = jwtUtils.generateJwtToken(usuario.getUsername());
                return ResponseEntity.ok(new AuthResponseDTO(jwt, usuario.getUsername(), usuario.getRol()));
            }
        }
        // Unauthorized
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
    }
}
