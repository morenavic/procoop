package ar.edu.undec.procoop.backend.repository;

import ar.edu.undec.procoop.backend.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repositorio JPA para la entidad Producto.
 */
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    List<Producto> findTop5ByOrderByIdProductoDesc();
}