package ar.edu.undec.procoop.backend.exception;

import org.springframework.http.HttpStatus;
import lombok.Getter;

/**
 * Excepción personalizada para errores de negocio.
 * Permite asociar un código HTTP a cada error para que el
 * GlobalExceptionHandler lo mapee correctamente en la respuesta REST.
 */
@Getter
public class AppException extends RuntimeException {

    private final HttpStatus estado;

    public AppException(String mensaje, HttpStatus estado) {
        super(mensaje);
        this.estado = estado;
    }

    public String getMensaje() {
        return "";
    }
}