package ar.edu.undec.procoop.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DTO de respuesta con los datos del perfil del cliente autenticado.
 */
@Getter
@AllArgsConstructor
public class PerfilResponseDTO {
    private Integer idUsuario;
    private String nombre;
    private String email;
    private String numeroCuenta;
    private String imagenUrl;
}