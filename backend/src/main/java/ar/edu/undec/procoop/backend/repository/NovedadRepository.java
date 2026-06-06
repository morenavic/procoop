package ar.edu.undec.procoop.backend.repository;

import ar.edu.undec.procoop.backend.entity.Novedad;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repositorio JPA para la entidad Novedad.
 */
public interface NovedadRepository extends JpaRepository<Novedad, Integer> {
    List<Novedad> findTop5ByOrderByFechaDesc();
}