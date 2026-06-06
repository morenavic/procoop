package ar.edu.undec.procoop.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidad que representa los roles del sistema.
 * Valores esperados: ADMINISTRADOR, CLIENTE.
 * Relación: un Rol puede pertenecer a muchos Usuarios.
 */
@Entity
@Table(name = "rol")
@Getter
@Setter
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Integer idRol;

    @Column(name = "nombre", nullable = false, unique = true, length = 50)
    private String nombre;
}