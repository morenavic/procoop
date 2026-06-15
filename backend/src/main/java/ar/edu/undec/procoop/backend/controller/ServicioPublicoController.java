package ar.edu.undec.procoop.backend.controller;

import ar.edu.undec.procoop.backend.dto.response.ServicioResponseDTO;
import ar.edu.undec.procoop.backend.service.ServicioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para servicios accesibles públicamente.
 * No requiere autenticación JWT.
 * Base path: /api/publico/servicios
 */
@RestController
@RequestMapping("/api/publico/servicios")
@RequiredArgsConstructor
public class ServicioPublicoController {

    private final ServicioService servicioService;

    @GetMapping
    public ResponseEntity<List<ServicioResponseDTO>> listar() {
        return ResponseEntity.ok(servicioService.listar());
    }
}