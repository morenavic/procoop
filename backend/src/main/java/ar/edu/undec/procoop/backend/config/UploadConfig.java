package ar.edu.undec.procoop.backend.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Configuración para el manejo de archivos subidos.
 *
 * Responsabilidades:
 *  - Crear los directorios de uploads al iniciar la aplicación
 *  - Exponer los archivos estáticos subidos como recursos HTTP
 *    para que el frontend pueda acceder a las imágenes por URL
 *
 * Los archivos se sirven bajo la ruta /uploads/**
 * y se leen desde el directorio configurado en app.uploads.dir
 */
@Configuration
public class UploadConfig implements WebMvcConfigurer {

    @Value("${app.uploads.dir}")
    private String uploadsDir;

    /**
     * Crea los subdirectorios necesarios al arrancar la aplicación.
     * Si ya existen, no hace nada.
     */
    @PostConstruct
    public void crearDirectorios() throws IOException {
        Files.createDirectories(Paths.get(uploadsDir, "novedades"));
        Files.createDirectories(Paths.get(uploadsDir, "productos"));
        Files.createDirectories(Paths.get(uploadsDir, "documentos"));
        Files.createDirectories(Paths.get(uploadsDir, "perfiles"));
    }

    /**
     * Expone los archivos del directorio de uploads como recursos estáticos.
     * Permite acceder a las imágenes desde: GET /uploads/novedades/imagen.jpg
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadsDir);
    }
}