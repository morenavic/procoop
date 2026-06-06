package ar.edu.undec.procoop.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO para crear o editar un documento.
 * El archivo se maneja por separado como MultipartFile en el controller.
 */
@Getter
@Setter
public class DocumentoRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    private String descripcion;
}