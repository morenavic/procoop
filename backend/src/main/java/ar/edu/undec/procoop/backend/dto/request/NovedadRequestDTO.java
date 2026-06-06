package ar.edu.undec.procoop.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

/**
 * DTO para crear o editar una novedad.
 * La imagen se maneja por separado como MultipartFile en el controller.
 */
@Getter
@Setter
public class NovedadRequestDTO {

    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    @NotBlank(message = "El tipo es obligatorio")
    @Pattern(regexp = "NOTICIA|EVENTO", message = "El tipo debe ser NOTICIA o EVENTO")
    private String tipo;
}