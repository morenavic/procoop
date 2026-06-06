package ar.edu.undec.procoop.backend.controller;

import ar.edu.undec.procoop.backend.dto.request.ProductoRequestDTO;
import ar.edu.undec.procoop.backend.dto.response.ProductoResponseDTO;
import ar.edu.undec.procoop.backend.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Controlador REST para la gestión de productos.
 *
 * Base path: /api/admin/productos
 * Requiere rol ADMINISTRADOR.
 * Usa multipart/form-data para soportar subida de imágenes.
 */
@RestController
@RequestMapping("/api/admin/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> listar() {
        return ResponseEntity.ok(productoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> obtener(@PathVariable Integer id) {
        return ResponseEntity.ok(productoService.obtenerPorId(id));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductoResponseDTO> crear(
            @Valid @RequestPart("datos") ProductoRequestDTO dto,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen) {
        return ResponseEntity.ok(productoService.crear(dto, imagen));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductoResponseDTO> editar(
            @PathVariable Integer id,
            @Valid @RequestPart("datos") ProductoRequestDTO dto,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen) {
        return ResponseEntity.ok(productoService.editar(id, dto, imagen));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}