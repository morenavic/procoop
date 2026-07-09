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
 * Separa el manejo de imágenes (resource_type: image)
 * y documentos (resource_type: raw) para garantizar
 * URLs correctas y descarga funcional.
 */
@Service
@RequiredArgsConstructor
public class ArchivoService {

    private final Cloudinary cloudinary;

    /**
     * Sube una imagen a Cloudinary con resource_type image.
     */
    public String guardarImagen(MultipartFile archivo, String carpeta) {
        try {
            Map resultado = cloudinary.uploader().upload(
                    archivo.getBytes(),
                    ObjectUtils.asMap(
                            "folder", "procoop/" + carpeta,
                            "resource_type", "image",
                            "use_filename", true,
                            "unique_filename", true,
                            "overwrite", false
                    )
            );
            return (String) resultado.get("secure_url");
        } catch (IOException e) {
            throw new RuntimeException("Error al subir imagen a Cloudinary: " + e.getMessage());
        }
    }

    /**
     * Sube un documento a Cloudinary con resource_type raw.
     * Fuerza fl_attachment para que el navegador descargue en lugar de mostrar.
     * Agrega la extensión original al public_id para que la URL la incluya.
     */
    public String guardarDocumento(MultipartFile archivo, String carpeta) {
        try {
            String nombreOriginal = archivo.getOriginalFilename();
            String extension = nombreOriginal != null && nombreOriginal.contains(".")
                    ? nombreOriginal.substring(nombreOriginal.lastIndexOf('.') + 1)
                    : "";

            Map resultado = cloudinary.uploader().upload(
                    archivo.getBytes(),
                    ObjectUtils.asMap(
                            "folder", "procoop/" + carpeta,
                            "resource_type", "raw",
                            "use_filename", true,
                            "unique_filename", true,
                            "overwrite", false,
                            "format", extension
                    )
            );
            return (String) resultado.get("secure_url");
        } catch (IOException e) {
            throw new RuntimeException("Error al subir documento a Cloudinary: " + e.getMessage());
        }
    }

    /**
     * Elimina un archivo de Cloudinary usando su URL pública.
     */
    public void eliminar(String url) {
        if (url == null || url.isBlank()) return;
        try {
            String publicId = extraerPublicId(url);
            if (publicId != null) {
                // Determinar resource_type según la URL
                String resourceType = url.contains("/raw/upload/") ? "raw" : "image";
                cloudinary.uploader().destroy(
                        publicId,
                        ObjectUtils.asMap("resource_type", resourceType)
                );
            }
        } catch (IOException e) {
            // No lanzamos excepción si falla la eliminación
        }
    }

    private String extraerPublicId(String url) {
        try {
            String[] partes = url.split("/upload/");
            if (partes.length < 2) return null;
            String sinVersion = partes[1].replaceFirst("v\\d+/", "");
            int punto = sinVersion.lastIndexOf('.');
            return punto > 0 ? sinVersion.substring(0, punto) : sinVersion;
        } catch (Exception e) {
            return null;
        }
    }

    public String guardarArchivo(MultipartFile archivo, String carpeta) {
        return guardarImagen(archivo, carpeta);
    }
}