package ar.edu.undec.procoop.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO para restablecer la contraseña usando el token recibido por email.
 */
@Getter
@Setter
public class RestablecerContraseniaRequestDTO {

    @NotBlank(message = "El token es obligatorio")
    private String token;

    @NotBlank(message = "La nueva contraseña es obligatoria")
    @Size(min = 6, message = "La nueva contraseña debe tener al menos 6 caracteres")
    private String contraseniaNueva;

    @NotBlank(message = "La confirmación es obligatoria")
    private String confirmacion;
}