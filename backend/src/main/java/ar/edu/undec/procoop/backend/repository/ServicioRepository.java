package ar.edu.undec.procoop.backend.repository;

import ar.edu.undec.procoop.backend.entity.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repositorio JPA para la entidad Servicio.
 */
public interface ServicioRepository extends JpaRepository<Servicio, Integer> {
    List<Servicio> findTop5ByOrderByIdServicioDesc();
}