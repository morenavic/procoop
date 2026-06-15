package ar.edu.undec.procoop.backend.controller;

import ar.edu.undec.procoop.backend.dto.request.ChatRequestDTO;
import ar.edu.undec.procoop.backend.dto.response.ChatResponseDTO;
import ar.edu.undec.procoop.backend.service.ChatbotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para el chatbot con IA.
 *
 * Base path: /api/cliente/chat
 * Requiere rol CLIENTE.
 */
@RestController
@RequestMapping("/api/cliente/chat")
@RequiredArgsConstructor
public class ChatbotController {

    private final ChatbotService chatbotService;

    /**
     * POST /api/cliente/chat
     * Recibe una consulta y devuelve la respuesta de Gemini.
     */
    @PostMapping
    public ResponseEntity<ChatResponseDTO> consultar(
            @Valid @RequestBody ChatRequestDTO dto) {
        return ResponseEntity.ok(chatbotService.consultar(dto));
    }
}