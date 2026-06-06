package ar.edu.undec.procoop.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO para crear o editar un producto.
 * El slug se genera automáticamente en el servicio desde el título.
 * La imagen se maneja por separado como MultipartFile en el controller.
 */
@Getter
@Setter
public class ProductoRequestDTO {

    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    private String subtitulo;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;
}