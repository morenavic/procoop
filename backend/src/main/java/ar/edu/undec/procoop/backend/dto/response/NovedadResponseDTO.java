package ar.edu.undec.procoop.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDate;

/**
 * DTO de respuesta para una novedad.
 * La imagenUrl es la URL completa para acceder a la imagen desde el frontend.
 */
@Getter
@AllArgsConstructor
public class NovedadResponseDTO {
    private Integer idNovedad;
    private String titulo;
    private String descripcion;
    private LocalDate fecha;
    private String imagenUrl;
    private String tipo;
}