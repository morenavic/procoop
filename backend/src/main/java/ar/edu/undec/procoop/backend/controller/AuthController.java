package ar.edu.undec.procoop.backend.controller;

import ar.edu.undec.procoop.backend.dto.request.LoginRequestDTO;
import ar.edu.undec.procoop.backend.dto.response.LoginResponseDTO;
import ar.edu.undec.procoop.backend.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para autenticación.
 * Endpoint público — no requiere token JWT.
 *
 * Base path: /api/auth
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * POST /api/auth/login
     * Autentica al usuario y devuelve el token JWT junto con sus datos básicos.
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(authService.iniciarSesion(request));
    }
}