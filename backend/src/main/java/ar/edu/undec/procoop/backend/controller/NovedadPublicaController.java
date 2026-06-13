package ar.edu.undec.procoop.backend.controller;

import ar.edu.undec.procoop.backend.dto.response.NovedadResponseDTO;
import ar.edu.undec.procoop.backend.service.NovedadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para novedades accesibles por el usuario cliente.
 *
 * Base path: /api/cliente/novedades
 * Requiere rol CLIENTE.
 *
 * Separa los endpoints del cliente de los del administrador
 * para mantener clara la separación de responsabilidades.
 */
@RestController
@RequestMapping("/api/cliente/novedades")
@RequiredArgsConstructor
public class NovedadPublicaController {

    private final NovedadService novedadService;

    /**
     * GET /api/cliente/novedades
     * Devuelve todas las novedades.
     */
    @GetMapping
    public ResponseEntity<List<NovedadResponseDTO>> listar() {
        return ResponseEntity.ok(novedadService.listar());
    }

    /**
     * GET /api/cliente/novedades/noticias
     * Devuelve solo las novedades de tipo NOTICIA.
     */
    @GetMapping("/noticias")
    public ResponseEntity<List<NovedadResponseDTO>> listarNoticias() {
        return ResponseEntity.ok(novedadService.listarPorTipo("NOTICIA"));
    }

    /**
     * GET /api/cliente/novedades/eventos
     * Devuelve solo las novedades de tipo EVENTO.
     */
    @GetMapping("/eventos")
    public ResponseEntity<List<NovedadResponseDTO>> listarEventos() {
        return ResponseEntity.ok(novedadService.listarPorTipo("EVENTO"));
    }
}