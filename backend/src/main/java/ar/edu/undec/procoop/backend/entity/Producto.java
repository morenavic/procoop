package ar.edu.undec.procoop.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidad que representa los productos ofrecidos por la empresa.
 * El slug es un identificador único amigable para URLs.
 * La imagen almacena la ruta relativa al archivo guardado en disco.
 */
@Entity
@Table(name = "producto")
@Getter
@Setter
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Integer idProducto;

    @Column(name = "slug", nullable = false, unique = true, length = 200)
    private String slug;

    @Column(name = "titulo", nullable = false, length = 200)
    private String titulo;

    @Column(name = "subtitulo", length = 300)
    private String subtitulo;

    @Column(name = "descripcion", nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "imagen", length = 255)
    private String imagen;
}