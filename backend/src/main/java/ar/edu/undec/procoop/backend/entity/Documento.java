package ar.edu.undec.procoop.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidad que representa documentos descargables por los clientes.
 * El campo archivo almacena la ruta relativa al archivo guardado en disco.
 * El campo tipo clasifica el documento: MANUAL, GUIA u OTRO.
 */
@Entity
@Table(name = "documento")
@Getter
@Setter
public class Documento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_documento")
    private Integer idDocumento;

    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "archivo", nullable = false, length = 255)
    private String archivo;

    @Column(name = "tipo", nullable = false, length = 20)
    private String tipo;
}