package ar.edu.undec.procoop.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DTO de respuesta para un cliente.
 */
@Getter
@AllArgsConstructor
public class ClienteResponseDTO {
    private Integer idUsuario;
    private String nombre;
    private String email;
    private String numeroCuenta;
    private String estado;
    private String imagenUrl;
}