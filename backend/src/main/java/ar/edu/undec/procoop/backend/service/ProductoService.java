package ar.edu.undec.procoop.backend.service;

import ar.edu.undec.procoop.backend.dto.request.ProductoRequestDTO;
import ar.edu.undec.procoop.backend.dto.response.ProductoResponseDTO;
import ar.edu.undec.procoop.backend.entity.Producto;
import ar.edu.undec.procoop.backend.exception.AppException;
import ar.edu.undec.procoop.backend.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.Normalizer;
import java.util.List;

/**
 * Servicio de gestión de productos.
 *
 * Responsabilidades:
 *  - CRUD completo de productos
 *  - Delegación del manejo de imágenes al ArchivoService
 *
 * Reglas de negocio:
 *  - El slug se genera automáticamente y debe ser único
 *  - La imagen es opcional al crear y editar
 *  - Al editar sin nueva imagen, se conserva la imagen existente
 *  - Al eliminar un producto, se elimina también su imagen del disco
 */
@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final ArchivoService archivoService;

    public List<ProductoResponseDTO> listar() {
        return productoRepository.findAll()
                .stream()
                .map(this::mapearAResponse)
                .toList();
    }

    public ProductoResponseDTO obtenerPorId(Integer id) {
        return mapearAResponse(buscarPorId(id));
    }

    public ProductoResponseDTO crear(ProductoRequestDTO dto, MultipartFile imagen) {
        Producto producto = new Producto();
        mapearDesdeDTO(dto, producto);

        if (imagen != null && !imagen.isEmpty()) {
            producto.setImagen(archivoService.guardarImagen(imagen, "productos"));
        }

        return mapearAResponse(productoRepository.save(producto));
    }

    public ProductoResponseDTO editar(Integer id, ProductoRequestDTO dto, MultipartFile imagen) {
        Producto producto = buscarPorId(id);

        mapearDesdeDTO(dto, producto);

        if (imagen != null && !imagen.isEmpty()) {
            archivoService.eliminar(producto.getImagen());
            producto.setImagen(archivoService.guardarImagen(imagen, "productos"));
        }

        return mapearAResponse(productoRepository.save(producto));
    }

    public void eliminar(Integer id) {
        Producto producto = buscarPorId(id);
        archivoService.eliminar(producto.getImagen());
        productoRepository.delete(producto);
    }

    private Producto buscarPorId(Integer id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new AppException(
                        "Producto no encontrado", HttpStatus.NOT_FOUND));
    }

    private void mapearDesdeDTO(ProductoRequestDTO dto, Producto producto) {
        producto.setTitulo(dto.getTitulo());
        producto.setSubtitulo(dto.getSubtitulo());
        producto.setDescripcion(dto.getDescripcion());
    }

    private ProductoResponseDTO mapearAResponse(Producto producto) {
        String imagenUrl = producto.getImagen() != null
                ? "http://localhost:8080/uploads/" + producto.getImagen()
                : null;

        return new ProductoResponseDTO(
                producto.getIdProducto(),
                producto.getTitulo(),
                producto.getSubtitulo(),
                producto.getDescripcion(),
                imagenUrl
        );
    }
}