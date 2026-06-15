package ar.edu.undec.procoop.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DTO de respuesta del chatbot.
 */
@Getter
@AllArgsConstructor
public class ChatResponseDTO {
    private String respuesta;
}