package ar.edu.undec.procoop.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO para cambiar el estado de un cliente.
 * Usado para activar o inactivar desde el panel de administración.
 */
@Getter
@Setter
public class ClienteActivacionDTO {

    @NotBlank(message = "El estado es obligatorio")
    @Pattern(regexp = "ACTIVO|INACTIVO|PENDIENTE",
            message = "El estado debe ser ACTIVO, INACTIVO o PENDIENTE")
    private String estado;
}