package ar.edu.undec.procoop.backend.controller;

import ar.edu.undec.procoop.backend.dto.request.RecuperarContraseniaRequestDTO;
import ar.edu.undec.procoop.backend.dto.request.RestablecerContraseniaRequestDTO;
import ar.edu.undec.procoop.backend.service.RecuperacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para recuperación de contraseña.
 *
 * Base path: /api/auth
 * Endpoints públicos — no requieren token JWT.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class RecuperacionController {

    private final RecuperacionService recuperacionService;

    /**
     * POST /api/auth/recuperar
     * Valida email + número de cuenta y envía el email de recuperación.
     */
    @PostMapping("/recuperar")
    public ResponseEntity<Void> recuperar(
            @Valid @RequestBody RecuperarContraseniaRequestDTO dto) {
        recuperacionService.solicitarRecuperacion(dto);
        return ResponseEntity.ok().build();
    }

    /**
     * POST /api/auth/restablecer
     * Valida el token y actualiza la contraseña.
     */
    @PostMapping("/restablecer")
    public ResponseEntity<Void> restablecer(
            @Valid @RequestBody RestablecerContraseniaRequestDTO dto) {
        recuperacionService.restablecerContrasenia(dto);
        return ResponseEntity.ok().build();
    }
}