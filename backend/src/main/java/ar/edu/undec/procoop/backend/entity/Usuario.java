package ar.edu.undec.procoop.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidad principal de usuarios del sistema.
 * Contempla tanto clientes como administradores mediante el campo rol.
 *
 * Estados posibles:
 *  - PENDIENTE: registrado pero sin activar
 *  - ACTIVO:    cuenta habilitada para operar
 *  - INACTIVO:  deshabilitado por el administrador
 *
 * El campo numeroCuenta es único y es el mecanismo de activación
 * automática cuando el usuario se registra coincidiendo con el
 * número cargado previamente por el administrador.
 */
@Entity
@Table(name = "usuario")
@Getter
@Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "contrasenia", nullable = false, length = 255)
    private String contrasenia;

    @Column(name = "numero_cuenta", nullable = false, unique = true, length = 50)
    private String numeroCuenta;

    @Column(name = "estado", nullable = false, length = 20)
    private String estado;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_rol", nullable = false)
    private Rol rol;
}