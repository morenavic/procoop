package ar.edu.undec.procoop.backend.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * Servicio de gestión de archivos usando Cloudinary.
 *
 * Reemplaza el almacenamiento en disco local por almacenamiento
 * en la nube, garantizando persistencia en producción.
 *
 * Las URLs devueltas por Cloudinary son públicas y permanentes.
 */
@Service
@RequiredArgsConstructor
public class ArchivoService {

    private final Cloudinary cloudinary;

    /**
     * Sube un archivo a Cloudinary y devuelve la URL pública.
     *
     * @param archivo   archivo recibido desde el formulario
     * @param carpeta   subcarpeta en Cloudinary (novedades, productos, documentos, perfiles)
     * @return URL pública del archivo en Cloudinary
     */
    public String guardarArchivo(MultipartFile archivo, String carpeta) {
        try {
            Map resultado = cloudinary.uploader().upload(
                    archivo.getBytes(),
                    ObjectUtils.asMap(
                            "folder", "procoop/" + carpeta,
                            "resource_type", "auto"
                    )
            );
            return (String) resultado.get("secure_url");
        } catch (IOException e) {
            throw new RuntimeException("Error al subir archivo a Cloudinary: " + e.getMessage());
        }
    }

    /**
     * Elimina un archivo de Cloudinary usando su URL pública.
     * Si la URL es nula o no es de Cloudinary, no hace nada.
     *
     * @param url URL pública del archivo en Cloudinary
     */
    public void eliminar(String url) {
        if (url == null || url.isBlank()) return;
        try {
            String publicId = extraerPublicId(url);
            if (publicId != null) {
                cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            }
        } catch (IOException e) {
            // No lanzamos excepción si falla la eliminación
        }
    }

    /**
     * Extrae el public_id de Cloudinary desde la URL completa.
     * Ejemplo: https://res.cloudinary.com/dj8dgicjj/image/upload/v123/procoop/productos/abc.jpg
     * → procoop/productos/abc
     */
    private String extraerPublicId(String url) {
        try {
            String[] partes = url.split("/upload/");
            if (partes.length < 2) return null;
            String conVersion = partes[1];
            // Remover versión si existe (v1234567/)
            String sinVersion = conVersion.replaceFirst("v\\d+/", "");
            // Remover extensión
            int punto = sinVersion.lastIndexOf('.');
            return punto > 0 ? sinVersion.substring(0, punto) : sinVersion;
        } catch (Exception e) {
            return null;
        }
    }

    // Métodos de compatibilidad con los servicios existentes
    public String guardarImagen(MultipartFile archivo, String carpeta) {
        return guardarArchivo(archivo, carpeta);
    }

    public String guardarDocumento(MultipartFile archivo, String carpeta) {
        return guardarArchivo(archivo, carpeta);
    }
}