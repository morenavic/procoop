package ar.edu.undec.procoop.backend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO para crear un cliente desde el panel de administración.
 * El admin carga el número de cuenta previamente.
 * El estado inicial siempre es PENDIENTE.
 */
@Getter
@Setter
public class ClienteRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene un formato válido")
    private String email;

    @NotBlank(message = "El número de cuenta es obligatorio")
    private String numeroCuenta;
}