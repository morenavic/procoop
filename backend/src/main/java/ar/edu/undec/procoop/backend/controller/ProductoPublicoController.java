package ar.edu.undec.procoop.backend.controller;

import ar.edu.undec.procoop.backend.dto.response.ProductoResponseDTO;
import ar.edu.undec.procoop.backend.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para productos accesibles públicamente.
 * No requiere autenticación JWT.
 * Base path: /api/publico/productos
 */
@RestController
@RequestMapping("/api/publico/productos")
@RequiredArgsConstructor
public class ProductoPublicoController {

    private final ProductoService productoService;

    /**
     * GET /api/publico/productos
     * Devuelve todos los productos disponibles.
     */
    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> listar() {
        return ResponseEntity.ok(productoService.listar());
    }
}