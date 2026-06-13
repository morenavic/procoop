package ar.edu.undec.procoop.backend.service;

import ar.edu.undec.procoop.backend.dto.request.DocumentoRequestDTO;
import ar.edu.undec.procoop.backend.dto.response.DocumentoResponseDTO;
import ar.edu.undec.procoop.backend.entity.Documento;
import ar.edu.undec.procoop.backend.exception.AppException;
import ar.edu.undec.procoop.backend.repository.DocumentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Servicio de gestión de documentos.
 *
 * Responsabilidades:
 *  - CRUD completo de documentos (admin)
 *  - Búsqueda por nombre y filtrado por tipo (cliente)
 *  - Delegación del manejo de archivos al ArchivoService
 */
@Service
@RequiredArgsConstructor
public class DocumentoService {

    private final DocumentoRepository documentoRepository;
    private final ArchivoService archivoService;

    public List<DocumentoResponseDTO> listar() {
        return documentoRepository.findAll()
                .stream()
                .map(this::mapearAResponse)
                .toList();
    }

    public List<DocumentoResponseDTO> buscarPorNombre(String nombre) {
        return documentoRepository.findByNombreContainingIgnoreCase(nombre)
                .stream()
                .map(this::mapearAResponse)
                .toList();
    }

    public List<DocumentoResponseDTO> filtrarPorTipo(String tipo) {
        return documentoRepository.findByTipo(tipo)
                .stream()
                .map(this::mapearAResponse)
                .toList();
    }

    public DocumentoResponseDTO obtenerPorId(Integer id) {
        return mapearAResponse(buscarPorId(id));
    }

    public DocumentoResponseDTO crear(DocumentoRequestDTO dto, MultipartFile archivo) {
        if (archivo == null || archivo.isEmpty()) {
            throw new AppException("El archivo es obligatorio", HttpStatus.BAD_REQUEST);
        }

        Documento documento = new Documento();
        documento.setNombre(dto.getNombre());
        documento.setDescripcion(dto.getDescripcion());
        documento.setTipo(dto.getTipo());
        documento.setArchivo(archivoService.guardarDocumento(archivo, "documentos"));

        return mapearAResponse(documentoRepository.save(documento));
    }

    public DocumentoResponseDTO editar(Integer id, DocumentoRequestDTO dto, MultipartFile archivo) {
        Documento documento = buscarPorId(id);
        documento.setNombre(dto.getNombre());
        documento.setDescripcion(dto.getDescripcion());
        documento.setTipo(dto.getTipo());

        if (archivo != null && !archivo.isEmpty()) {
            archivoService.eliminar(documento.getArchivo());
            documento.setArchivo(archivoService.guardarDocumento(archivo, "documentos"));
        }

        return mapearAResponse(documentoRepository.save(documento));
    }

    public void eliminar(Integer id) {
        Documento documento = buscarPorId(id);
        archivoService.eliminar(documento.getArchivo());
        documentoRepository.delete(documento);
    }

    private Documento buscarPorId(Integer id) {
        return documentoRepository.findById(id)
                .orElseThrow(() -> new AppException(
                        "Documento no encontrado", HttpStatus.NOT_FOUND));
    }

    private DocumentoResponseDTO mapearAResponse(Documento documento) {
        String archivoUrl = documento.getArchivo() != null
                ? "http://localhost:8080/uploads/" + documento.getArchivo()
                : null;

        return new DocumentoResponseDTO(
                documento.getIdDocumento(),
                documento.getNombre(),
                documento.getDescripcion(),
                documento.getTipo(),
                archivoUrl
        );
    }
}