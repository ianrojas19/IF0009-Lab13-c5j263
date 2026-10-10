package com.medpharm.cr.medpharm_backend.controller;

import com.medpharm.cr.medpharm_backend.model.RecetaMedica;
import com.medpharm.cr.medpharm_backend.service.RecetaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/recetas")
@CrossOrigin(origins = "*")
public class RecetaController {

    private final RecetaService recetaService;

    public RecetaController(RecetaService recetaService) {
        this.recetaService = recetaService;
    }

    @GetMapping
    public ResponseEntity<List<RecetaMedica>> obtenerTodas() {
        return ResponseEntity.ok(recetaService.obtenerTodas());
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<RecetaMedica>> obtenerPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(recetaService.obtenerPorEstado(estado));
    }

    @PostMapping
    public ResponseEntity<?> registrarReceta(@RequestBody RecetaMedica receta,
            @RequestHeader("Authorization") String authHeader) {
        try {
            RecetaMedica nuevaReceta = recetaService.registrarReceta(receta, authHeader);
            return ResponseEntity.ok(nuevaReceta);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> cambiarEstado(@PathVariable Long id, @RequestBody Map<String, String> body) {
        try {
            String nuevoEstado = body.get("estado");
            RecetaMedica recetaActualizada = recetaService.cambiarEstado(id, nuevoEstado);
            return ResponseEntity.ok(recetaActualizada);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
