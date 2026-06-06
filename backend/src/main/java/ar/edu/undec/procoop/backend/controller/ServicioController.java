package ar.edu.undec.procoop.backend.controller;

import ar.edu.undec.procoop.backend.dto.request.ServicioRequestDTO;
import ar.edu.undec.procoop.backend.dto.response.ServicioResponseDTO;
import ar.edu.undec.procoop.backend.service.ServicioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de servicios.
 *
 * Base path: /api/admin/servicios
 * Requiere rol ADMINISTRADOR.
 * Usa JSON simple (sin multipart) ya que no maneja archivos.
 */
@RestController
@RequestMapping("/api/admin/servicios")
@RequiredArgsConstructor
public class ServicioController {

    private final ServicioService servicioService;

    @GetMapping
    public ResponseEntity<List<ServicioResponseDTO>> listar() {
        return ResponseEntity.ok(servicioService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicioResponseDTO> obtener(@PathVariable Integer id) {
        return ResponseEntity.ok(servicioService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<ServicioResponseDTO> crear(
            @Valid @RequestBody ServicioRequestDTO dto) {
        return ResponseEntity.ok(servicioService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicioResponseDTO> editar(
            @PathVariable Integer id,
            @Valid @RequestBody ServicioRequestDTO dto) {
        return ResponseEntity.ok(servicioService.editar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        servicioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}