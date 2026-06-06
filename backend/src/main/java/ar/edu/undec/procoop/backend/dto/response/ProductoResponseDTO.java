package ar.edu.undec.procoop.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DTO de respuesta para un producto.
 * imagenUrl es la URL completa para acceder a la imagen desde el frontend.
 */
@Getter
@AllArgsConstructor
public class ProductoResponseDTO {
    private Integer idProducto;
    private String titulo;
    private String subtitulo;
    private String descripcion;
    private String imagenUrl;
}