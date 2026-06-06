package ar.edu.undec.procoop.backend.service;

import ar.edu.undec.procoop.backend.exception.AppException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

/**
 * Servicio para el manejo de archivos subidos al servidor.
 *
 * Responsabilidades:
 *  - Validar tipo y tamaño de archivos
 *  - Guardar archivos en disco con nombre único (UUID)
 *  - Eliminar archivos del disco
 *
 * Los archivos se guardan en: {uploadsDir}/{subcarpeta}/{uuid}.{extension}
 * La ruta relativa guardada en BD es: {subcarpeta}/{uuid}.{extension}
 */
@Service
public class ArchivoService {

    private static final List<String> TIPOS_IMAGEN_PERMITIDOS =
            List.of("image/jpeg", "image/png", "image/webp");

    private static final List<String> TIPOS_DOCUMENTO_PERMITIDOS =
            List.of("application/pdf",
                    "application/msword",
                    "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                    "application/vnd.ms-excel",
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

    private static final long MAX_TAMANIO_IMAGEN = 5 * 1024 * 1024;      // 5MB
    private static final long MAX_TAMANIO_DOCUMENTO = 20 * 1024 * 1024;  // 20MB

    @Value("${app.uploads.dir}")
    private String uploadsDir;

    /**
     * Guarda una imagen en la subcarpeta indicada.
     * Valida tipo MIME y tamaño antes de guardar.
     *
     * @param archivo    archivo recibido del frontend
     * @param subcarpeta subcarpeta destino (ej: "novedades", "productos")
     * @return ruta relativa guardada en BD (ej: "novedades/uuid.jpg")
     */
    public String guardarImagen(MultipartFile archivo, String subcarpeta) {
        validarArchivo(archivo, TIPOS_IMAGEN_PERMITIDOS, MAX_TAMANIO_IMAGEN,
                "Solo se permiten imágenes JPG, PNG o WEBP de hasta 5MB");
        return guardar(archivo, subcarpeta);
    }

    /**
     * Guarda un documento en la subcarpeta indicada.
     * Valida tipo MIME y tamaño antes de guardar.
     */
    public String guardarDocumento(MultipartFile archivo, String subcarpeta) {
        validarArchivo(archivo, TIPOS_DOCUMENTO_PERMITIDOS, MAX_TAMANIO_DOCUMENTO,
                "Solo se permiten documentos PDF, Word o Excel de hasta 20MB");
        return guardar(archivo, subcarpeta);
    }

    /**
     * Elimina un archivo del disco dado su ruta relativa.
     * Si no existe, no lanza error (operación idempotente).
     */
    public void eliminar(String rutaRelativa) {
        if (rutaRelativa == null || rutaRelativa.isBlank()) return;
        try {
            Path ruta = Paths.get(uploadsDir, rutaRelativa);
            Files.deleteIfExists(ruta);
        } catch (IOException e) {
            // Se loguea pero no se lanza excepción — no es crítico
        }
    }

    private String guardar(MultipartFile archivo, String subcarpeta) {
        try {
            String extension = obtenerExtension(archivo.getOriginalFilename());
            String nombreArchivo = UUID.randomUUID() + "." + extension;
            Path destino = Paths.get(uploadsDir, subcarpeta, nombreArchivo);
            Files.copy(archivo.getInputStream(), destino);
            return subcarpeta + "/" + nombreArchivo;
        } catch (IOException e) {
            throw new AppException("Error al guardar el archivo", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void validarArchivo(MultipartFile archivo, List<String> tiposPermitidos,
                                long maxTamanio, String mensajeError) {
        if (archivo == null || archivo.isEmpty()) {
            throw new AppException("El archivo no puede estar vacío", HttpStatus.BAD_REQUEST);
        }
        if (!tiposPermitidos.contains(archivo.getContentType())) {
            throw new AppException(mensajeError, HttpStatus.BAD_REQUEST);
        }
        if (archivo.getSize() > maxTamanio) {
            throw new AppException(mensajeError, HttpStatus.BAD_REQUEST);
        }
    }

    private String obtenerExtension(String nombreArchivo) {
        if (nombreArchivo == null || !nombreArchivo.contains(".")) return "bin";
        return nombreArchivo.substring(nombreArchivo.lastIndexOf('.') + 1).toLowerCase();
    }
}