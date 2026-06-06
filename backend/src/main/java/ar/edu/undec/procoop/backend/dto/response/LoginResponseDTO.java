package ar.edu.undec.procoop.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DTO de respuesta al login exitoso.
 * Contiene el token JWT y los datos mínimos del usuario
 * que el frontend necesita para construir la sesión.
 */
@Getter
@AllArgsConstructor
public class LoginResponseDTO {
    private String token;
    private String nombreUsuario;
    private String email;
    private String rol;
}