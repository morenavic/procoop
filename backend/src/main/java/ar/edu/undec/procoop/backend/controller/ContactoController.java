package ar.edu.undec.procoop.backend.controller;

import ar.edu.undec.procoop.backend.dto.request.ContactoRequestDTO;
import ar.edu.undec.procoop.backend.service.ContactoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para el formulario de contacto público.
 * Base path: /api/publico/contacto
 * No requiere autenticación JWT.
 */
@RestController
@RequestMapping("/api/publico/contacto")
@RequiredArgsConstructor
public class ContactoController {

    private final ContactoService contactoService;

    @PostMapping
    public ResponseEntity<Void> enviarConsulta(
            @Valid @RequestBody ContactoRequestDTO dto) {
        contactoService.enviarConsulta(dto);
        return ResponseEntity.ok().build();
    }
}