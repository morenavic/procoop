package ar.edu.undec.procoop.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DTO que representa una actividad reciente en el dashboard.
 * Unifica distintos tipos de registros (novedades, productos, etc.)
 * en una estructura común para mostrar en la tabla de actividad.
 */
@Getter
@AllArgsConstructor
public class ActividadRecienteDTO {
    private String tipo;
    private String titulo;
    private String descripcion;
}