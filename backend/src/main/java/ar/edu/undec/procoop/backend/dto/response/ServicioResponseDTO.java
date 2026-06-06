package ar.edu.undec.procoop.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DTO de respuesta para un servicio.
 */
@Getter
@AllArgsConstructor
public class ServicioResponseDTO {
    private Integer idServicio;
    private String titulo;
    private String descripcion;
}