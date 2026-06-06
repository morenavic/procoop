package ar.edu.undec.procoop.backend.repository;

import ar.edu.undec.procoop.backend.entity.Documento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repositorio JPA para la entidad Documento.
 */
public interface DocumentoRepository extends JpaRepository<Documento, Integer> {
    List<Documento> findTop5ByOrderByIdDocumentoDesc();
}