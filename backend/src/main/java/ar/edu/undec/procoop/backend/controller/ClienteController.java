package ar.edu.undec.procoop.backend.controller;

import ar.edu.undec.procoop.backend.dto.request.ClienteActivacionDTO;
import ar.edu.undec.procoop.backend.dto.request.ClienteRequestDTO;
import ar.edu.undec.procoop.backend.dto.response.ClienteResponseDTO;
import ar.edu.undec.procoop.backend.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de clientes.
 *
 * Base path: /api/admin/clientes
 * Requiere rol ADMINISTRADOR.
 */
@RestController
@RequestMapping("/api/admin/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listar() {
        return ResponseEntity.ok(clienteService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> obtener(@PathVariable Integer id) {
        return ResponseEntity.ok(clienteService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> crear(
            @Valid @RequestBody ClienteRequestDTO dto) {
        return ResponseEntity.ok(clienteService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> editar(
            @PathVariable Integer id,
            @Valid @RequestBody ClienteRequestDTO dto) {
        return ResponseEntity.ok(clienteService.editar(id, dto));
    }

    /**
     * PATCH /api/admin/clientes/{id}/estado
     * Cambia el estado del cliente: ACTIVO, INACTIVO o PENDIENTE.
     * Cubre el flujo de activación manual del CU26.
     */
    @PatchMapping("/{id}/estado")
    public ResponseEntity<ClienteResponseDTO> cambiarEstado(
            @PathVariable Integer id,
            @Valid @RequestBody ClienteActivacionDTO dto) {
        return ResponseEntity.ok(clienteService.cambiarEstado(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        clienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}