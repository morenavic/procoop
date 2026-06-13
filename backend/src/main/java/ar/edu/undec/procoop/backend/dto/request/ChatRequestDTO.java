package ar.edu.undec.procoop.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO para la consulta al chatbot.
 */
@Getter
@Setter
public class ChatRequestDTO {

    @NotBlank(message = "La consulta no puede estar vacía")
    private String consulta;
}