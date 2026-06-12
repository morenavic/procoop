package ar.edu.undec.procoop.backend.controller;

import ar.edu.undec.procoop.backend.dto.request.CambiarContraseniaRequestDTO;
import ar.edu.undec.procoop.backend.dto.response.PerfilResponseDTO;
import ar.edu.undec.procoop.backend.service.PerfilService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controlador REST para el perfil del cliente autenticado.
 *
 * Base path: /api/cliente/perfil
 * Requiere rol CLIENTE.
 *
 * El email del usuario autenticado se obtiene del token JWT
 * a través del objeto Authentication de Spring Security.
 */
@RestController
@RequestMapping("/api/cliente/perfil")
@RequiredArgsConstructor
public class PerfilController {

    private final PerfilService perfilService;

    /**
     * GET /api/cliente/perfil
     * Devuelve los datos del perfil del cliente autenticado.
     */
    @GetMapping
    public ResponseEntity<PerfilResponseDTO> obtener(Authentication auth) {
        return ResponseEntity.ok(perfilService.obtenerPerfil(auth.getName()));
    }

    /**
     * PATCH /api/cliente/perfil/foto
     * Actualiza la foto de perfil del cliente autenticado.
     */
    @PatchMapping(value = "/foto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PerfilResponseDTO> actualizarFoto(
            Authentication auth,
            @RequestPart("imagen") MultipartFile imagen) {
        return ResponseEntity.ok(perfilService.actualizarFoto(auth.getName(), imagen));
    }

    /**
     * PATCH /api/cliente/perfil/contrasenia
     * Cambia la contraseña del cliente autenticado.
     */
    @PatchMapping("/contrasenia")
    public ResponseEntity<Void> cambiarContrasenia(
            Authentication auth,
            @Valid @RequestBody CambiarContraseniaRequestDTO dto) {
        perfilService.cambiarContrasenia(auth.getName(), dto);
        return ResponseEntity.ok().build();
    }
}