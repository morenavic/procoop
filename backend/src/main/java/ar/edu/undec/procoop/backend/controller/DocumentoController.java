package ar.edu.undec.procoop.backend.controller;

import ar.edu.undec.procoop.backend.dto.request.DocumentoRequestDTO;
import ar.edu.undec.procoop.backend.dto.response.DocumentoResponseDTO;
import ar.edu.undec.procoop.backend.service.DocumentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Controlador REST para la gestión de documentos.
 *
 * Base path: /api/admin/documentos
 * Requiere rol ADMINISTRADOR.
 * Usa multipart/form-data para soportar subida de archivos.
 */
@RestController
@RequestMapping("/api/admin/documentos")
@RequiredArgsConstructor
public class DocumentoController {

    private final DocumentoService documentoService;

    @GetMapping
    public ResponseEntity<List<DocumentoResponseDTO>> listar() {
        return ResponseEntity.ok(documentoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentoResponseDTO> obtener(@PathVariable Integer id) {
        return ResponseEntity.ok(documentoService.obtenerPorId(id));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DocumentoResponseDTO> crear(
            @Valid @RequestPart("datos") DocumentoRequestDTO dto,
            @RequestPart("archivo") MultipartFile archivo) {
        return ResponseEntity.ok(documentoService.crear(dto, archivo));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DocumentoResponseDTO> editar(
            @PathVariable Integer id,
            @Valid @RequestPart("datos") DocumentoRequestDTO dto,
            @RequestPart(value = "archivo", required = false) MultipartFile archivo) {
        return ResponseEntity.ok(documentoService.editar(id, dto, archivo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        documentoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}