package ar.edu.undec.procoop.backend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO para solicitar la recuperación de contraseña.
 * Se valida email y número de cuenta para mayor seguridad.
 */
@Getter
@Setter
public class RecuperarContraseniaRequestDTO {

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene un formato válido")
    private String email;

    @NotBlank(message = "El número de cuenta es obligatorio")
    private String numeroCuenta;
}