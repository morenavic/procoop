package ar.edu.undec.procoop.backend.service;

import ar.edu.undec.procoop.backend.dto.request.ServicioRequestDTO;
import ar.edu.undec.procoop.backend.dto.response.ServicioResponseDTO;
import ar.edu.undec.procoop.backend.entity.Servicio;
import ar.edu.undec.procoop.backend.exception.AppException;
import ar.edu.undec.procoop.backend.repository.ServicioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio de gestión de servicios.
 * CRUD simple sin manejo de archivos ni slugs.
 */
@Service
@RequiredArgsConstructor
public class ServicioService {

    private final ServicioRepository servicioRepository;

    public List<ServicioResponseDTO> listar() {
        return servicioRepository.findAll()
                .stream()
                .map(this::mapearAResponse)
                .toList();
    }

    public ServicioResponseDTO obtenerPorId(Integer id) {
        return mapearAResponse(buscarPorId(id));
    }

    public ServicioResponseDTO crear(ServicioRequestDTO dto) {
        Servicio servicio = new Servicio();
        mapearDesdeDTO(dto, servicio);
        return mapearAResponse(servicioRepository.save(servicio));
    }

    public ServicioResponseDTO editar(Integer id, ServicioRequestDTO dto) {
        Servicio servicio = buscarPorId(id);
        mapearDesdeDTO(dto, servicio);
        return mapearAResponse(servicioRepository.save(servicio));
    }

    public void eliminar(Integer id) {
        servicioRepository.delete(buscarPorId(id));
    }

    private Servicio buscarPorId(Integer id) {
        return servicioRepository.findById(id)
                .orElseThrow(() -> new AppException(
                        "Servicio no encontrado", HttpStatus.NOT_FOUND));
    }

    private void mapearDesdeDTO(ServicioRequestDTO dto, Servicio servicio) {
        servicio.setTitulo(dto.getTitulo());
        servicio.setDescripcion(dto.getDescripcion());
    }

    private ServicioResponseDTO mapearAResponse(Servicio servicio) {
        return new ServicioResponseDTO(
                servicio.getIdServicio(),
                servicio.getTitulo(),
                servicio.getDescripcion()
        );
    }
}