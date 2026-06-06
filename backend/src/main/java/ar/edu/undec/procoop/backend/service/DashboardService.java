package ar.edu.undec.procoop.backend.service;

import ar.edu.undec.procoop.backend.dto.response.ActividadRecienteDTO;
import ar.edu.undec.procoop.backend.dto.response.DashboardResponseDTO;
import ar.edu.undec.procoop.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio del dashboard del administrador.
 *
 * Responsabilidades:
 *  - Calcular métricas generales del sistema
 *  - Recopilar actividades recientes de todas las entidades
 *
 * Las actividades recientes se obtienen tomando los últimos 5 registros
 * de cada entidad y combinándolos en una sola lista unificada.
 */
@Service
@RequiredArgsConstructor
public class DashboardService {

    private final UsuarioRepository usuarioRepository;
    private final DocumentoRepository documentoRepository;
    private final ProductoRepository productoRepository;
    private final NovedadRepository novedadRepository;
    private final ServicioRepository servicioRepository;

    public DashboardResponseDTO obtenerDashboard() {
        long totalClientesActivos = usuarioRepository
                .countByEstadoAndRolNombre("ACTIVO", "CLIENTE");

        long totalDocumentos = documentoRepository.count();
        long totalProductos = productoRepository.count();

        List<ActividadRecienteDTO> actividades = new ArrayList<>();

        novedadRepository.findTop5ByOrderByFechaDesc()
                .forEach(n -> actividades.add(
                        new ActividadRecienteDTO("Novedad", n.getTitulo(), n.getTipo())));

        productoRepository.findTop5ByOrderByIdProductoDesc()
                .forEach(p -> actividades.add(
                        new ActividadRecienteDTO("Producto", p.getTitulo(), p.getSubtitulo())));

        servicioRepository.findTop5ByOrderByIdServicioDesc()
                .forEach(s -> actividades.add(
                        new ActividadRecienteDTO("Servicio", s.getTitulo(), null)));

        documentoRepository.findTop5ByOrderByIdDocumentoDesc()
                .forEach(d -> actividades.add(
                        new ActividadRecienteDTO("Documento", d.getNombre(), d.getDescripcion())));

        return new DashboardResponseDTO(
                totalClientesActivos,
                totalDocumentos,
                totalProductos,
                actividades
        );
    }
}