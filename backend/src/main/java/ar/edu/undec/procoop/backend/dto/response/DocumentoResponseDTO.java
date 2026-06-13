package ar.edu.undec.procoop.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DTO de respuesta para un documento.
 */
@Getter
@AllArgsConstructor
public class DocumentoResponseDTO {
    private Integer idDocumento;
    private String nombre;
    private String descripcion;
    private String tipo;
    private String archivoUrl;
}