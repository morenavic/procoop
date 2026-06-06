package ar.edu.undec.procoop.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

/**
 * Entidad que representa noticias y eventos de la empresa.
 * El campo tipo distingue entre NOTICIA y EVENTO.
 * La imagen almacena la ruta relativa al archivo guardado en disco.
 */
@Entity
@Table(name = "novedad")
@Getter
@Setter
public class Novedad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_novedad")
    private Integer idNovedad;

    @Column(name = "titulo", nullable = false, length = 200)
    private String titulo;

    @Column(name = "descripcion", nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "imagen", length = 255)
    private String imagen;

    @Column(name = "tipo", nullable = false, length = 10)
    private String tipo;
}