package ar.edu.undec.procoop.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DTO de respuesta para un documento.
 * archivoUrl es la URL completa para descargar el archivo desde el frontend.
 */
@Getter
@AllArgsConstructor
public class DocumentoResponseDTO {
    private Integer idDocumento;
    private String nombre;
    private String descripcion;
    private String archivoUrl;
}