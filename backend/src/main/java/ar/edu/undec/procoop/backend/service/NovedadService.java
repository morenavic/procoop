package ar.edu.undec.procoop.backend.service;

import ar.edu.undec.procoop.backend.dto.request.NovedadRequestDTO;
import ar.edu.undec.procoop.backend.dto.response.NovedadResponseDTO;
import ar.edu.undec.procoop.backend.entity.Novedad;
import ar.edu.undec.procoop.backend.exception.AppException;
import ar.edu.undec.procoop.backend.repository.NovedadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Servicio de gestión de novedades.
 *
 * Responsabilidades:
 *  - CRUD completo de novedades
 *  - Delegación del manejo de imágenes al ArchivoService
 *  - Construcción de URLs absolutas para las imágenes
 *
 * Reglas de negocio:
 *  - La imagen es opcional al crear y editar
 *  - Al editar sin nueva imagen, se conserva la imagen existente
 *  - Al eliminar una novedad, se elimina también su imagen del disco
 */
@Service
@RequiredArgsConstructor
public class NovedadService {

    private final NovedadRepository novedadRepository;
    private final ArchivoService archivoService;

    @Value("${FRONTEND_URL:http://localhost:8080}")
    private String baseUrl;

    public List<NovedadResponseDTO> listar() {
        return novedadRepository.findAll()
                .stream()
                .map(this::mapearAResponse)
                .toList();
    }

    public NovedadResponseDTO obtenerPorId(Integer id) {
        return mapearAResponse(buscarPorId(id));
    }

    public NovedadResponseDTO crear(NovedadRequestDTO dto, MultipartFile imagen) {
        Novedad novedad = new Novedad();
        mapearDesdDTO(dto, novedad);

        if (imagen != null && !imagen.isEmpty()) {
            novedad.setImagen(archivoService.guardarImagen(imagen, "novedades"));
        }

        return mapearAResponse(novedadRepository.save(novedad));
    }

    public NovedadResponseDTO editar(Integer id, NovedadRequestDTO dto, MultipartFile imagen) {
        Novedad novedad = buscarPorId(id);
        mapearDesdDTO(dto, novedad);

        if (imagen != null && !imagen.isEmpty()) {
            // Eliminar imagen anterior si existe
            archivoService.eliminar(novedad.getImagen());
            novedad.setImagen(archivoService.guardarImagen(imagen, "novedades"));
        }

        return mapearAResponse(novedadRepository.save(novedad));
    }

    public void eliminar(Integer id) {
        Novedad novedad = buscarPorId(id);
        archivoService.eliminar(novedad.getImagen());
        novedadRepository.delete(novedad);
    }

    private Novedad buscarPorId(Integer id) {
        return novedadRepository.findById(id)
                .orElseThrow(() -> new AppException(
                        "Novedad no encontrada", HttpStatus.NOT_FOUND));
    }

    private void mapearDesdDTO(NovedadRequestDTO dto, Novedad novedad) {
        novedad.setTitulo(dto.getTitulo());
        novedad.setDescripcion(dto.getDescripcion());
        novedad.setFecha(dto.getFecha());
        novedad.setTipo(dto.getTipo());
    }

    public List<NovedadResponseDTO> listarPorTipo(String tipo) {
        return novedadRepository.findByTipoOrderByFechaDesc(tipo)
                .stream()
                .map(this::mapearAResponse)
                .toList();
    }

    /**
     * Construye la URL absoluta de la imagen para que el frontend pueda mostrarla.
     * Si no hay imagen, devuelve null.
     */
    private NovedadResponseDTO mapearAResponse(Novedad novedad) {
        String imagenUrl = novedad.getImagen() != null
                ? "http://localhost:8080/uploads/" + novedad.getImagen()
                : null;

        return new NovedadResponseDTO(
                novedad.getIdNovedad(),
                novedad.getTitulo(),
                novedad.getDescripcion(),
                novedad.getFecha(),
                imagenUrl,
                novedad.getTipo()
        );
    }
}