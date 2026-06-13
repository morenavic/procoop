package ar.edu.undec.procoop.backend.repository;

import ar.edu.undec.procoop.backend.entity.TokenRecuperacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio JPA para la entidad TokenRecuperacion.
 */
public interface TokenRecuperacionRepository extends JpaRepository<TokenRecuperacion, Integer> {
    Optional<TokenRecuperacion> findByToken(String token);
}