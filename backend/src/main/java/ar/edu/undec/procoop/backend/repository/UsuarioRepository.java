package ar.edu.undec.procoop.backend.repository;

import ar.edu.undec.procoop.backend.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio JPA para la entidad Usuario.
 * Provee acceso a la base de datos sin necesidad de SQL manual
 * para las operaciones comunes.
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByNumeroCuenta(String numeroCuenta);

    boolean existsByEmail(String email);

    boolean existsByNumeroCuenta(String numeroCuenta);
}