package ar.edu.undec.procoop.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

/**
 * DTO de respuesta del dashboard del administrador.
 * Contiene métricas generales y actividades recientes del sistema.
 */
@Getter
@AllArgsConstructor
public class DashboardResponseDTO {
    private long totalClientesActivos;
    private long totalDocumentos;
    private long totalProductos;
    private List<ActividadRecienteDTO> actividadesRecientes;
}