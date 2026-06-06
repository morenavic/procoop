package ar.edu.undec.procoop.backend.controller;

import ar.edu.undec.procoop.backend.dto.request.NovedadRequestDTO;
import ar.edu.undec.procoop.backend.dto.response.NovedadResponseDTO;
import ar.edu.undec.procoop.backend.service.NovedadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Controlador REST para la gestión de novedades.
 *
 * Base path: /api/admin/novedades
 * Requiere rol ADMINISTRADOR.
 *
 * Usa multipart/form-data para soportar subida de imágenes
 * junto con los datos del formulario en la misma request.
 */
@RestController
@RequestMapping("/api/admin/novedades")
@RequiredArgsConstructor
public class NovedadController {

    private final NovedadService novedadService;

    @GetMapping
    public ResponseEntity<List<NovedadResponseDTO>> listar() {
        return ResponseEntity.ok(novedadService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NovedadResponseDTO> obtener(@PathVariable Integer id) {
        return ResponseEntity.ok(novedadService.obtenerPorId(id));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<NovedadResponseDTO> crear(
            @Valid @RequestPart("datos") NovedadRequestDTO dto,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen) {
        return ResponseEntity.ok(novedadService.crear(dto, imagen));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<NovedadResponseDTO> editar(
            @PathVariable Integer id,
            @Valid @RequestPart("datos") NovedadRequestDTO dto,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen) {
        return ResponseEntity.ok(novedadService.editar(id, dto, imagen));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        novedadService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}