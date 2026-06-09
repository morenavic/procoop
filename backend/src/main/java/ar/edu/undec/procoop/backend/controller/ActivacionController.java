package ar.edu.undec.procoop.backend.controller;

import ar.edu.undec.procoop.backend.dto.request.ActivacionRequestDTO;
import ar.edu.undec.procoop.backend.service.ActivacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para la activación de cuentas.
 *
 * Base path: /api/auth/activar
 * Endpoint público — no requiere token JWT.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class ActivacionController {

    private final ActivacionService activacionService;

    /**
     * POST /api/auth/activar
     * Activa la cuenta del usuario cliente validando el número de cuenta.
     */
    @PostMapping("/activar")
    public ResponseEntity<Void> activar(@Valid @RequestBody ActivacionRequestDTO dto) {
        activacionService.activarCuenta(dto);
        return ResponseEntity.ok().build();
    }
}