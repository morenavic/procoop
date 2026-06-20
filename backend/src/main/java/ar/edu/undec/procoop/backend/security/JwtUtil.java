package ar.edu.undec.procoop.backend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * Utilidad para generación y validación de tokens JWT.
 *
 * El token incluye:
 *  - subject:  email del usuario
 *  - rol:      nombre del rol (ADMINISTRADOR | CLIENTE)
 *  - idUsuario: identificador numérico
 *
 * La clave secreta y el tiempo de expiración se leen desde
 * variables de entorno definidas en application.yml.
 */
@Component
public class JwtUtil {

    private final SecretKey clave;
    private final long expiracion;

    public JwtUtil(
            @Value("${JWT_SECRET}") String secreto,
            @Value("${JWT_EXPIRATION}") long expiracion) {
        this.clave = Keys.hmacShaKeyFor(secreto.getBytes());
        this.expiracion = expiracion;
    }

    /** Genera un token JWT firmado para el usuario autenticado. */
    public String generarToken(String email, String rol, Integer idUsuario) {
        return Jwts.builder()
                .subject(email)
                .claim("rol", rol)
                .claim("idUsuario", idUsuario)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiracion))
                .signWith(clave)
                .compact();
    }

    /** Extrae el email (subject) del token. */
    public String obtenerEmail(String token) {
        return parsear(token).getPayload().getSubject();
    }

    /** Extrae el rol del token. */
    public String obtenerRol(String token) {
        return parsear(token).getPayload().get("rol", String.class);
    }

    /** Valida que el token sea legítimo y no haya expirado. */
    public boolean esValido(String token) {
        try {
            parsear(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Jws<Claims> parsear(String token) {
        return Jwts.parser()
                .verifyWith(clave)
                .build()
                .parseSignedClaims(token);
    }
}