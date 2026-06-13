package ar.edu.undec.procoop.backend.controller;

import ar.edu.undec.procoop.backend.dto.response.DocumentoResponseDTO;
import ar.edu.undec.procoop.backend.service.DocumentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para documentos accesibles por el usuario cliente.
 *
 * Base path: /api/cliente/documentos
 * Requiere rol CLIENTE.
 *
 * Soporta listado completo, búsqueda por nombre y filtrado por tipo.
 */
@RestController
@RequestMapping("/api/cliente/documentos")
@RequiredArgsConstructor
public class DocumentoPublicoController {

    private final DocumentoService documentoService;

    /**
     * GET /api/cliente/documentos
     * Parámetros opcionales:
     *  - buscar: filtra por nombre (CU19)
     *  - tipo:   filtra por tipo MANUAL|GUIA|OTRO (CU21)
     */
    @GetMapping
    public ResponseEntity<List<DocumentoResponseDTO>> listar(
            @RequestParam(required = false) String buscar,
            @RequestParam(required = false) String tipo) {

        if (buscar != null && !buscar.isBlank()) {
            return ResponseEntity.ok(documentoService.buscarPorNombre(buscar));
        }

        if (tipo != null && !tipo.isBlank() && !tipo.equals("TODOS")) {
            return ResponseEntity.ok(documentoService.filtrarPorTipo(tipo));
        }

        return ResponseEntity.ok(documentoService.listar());
    }
}