package ar.edu.undec.procoop.backend.service;

import ar.edu.undec.procoop.backend.dto.request.RecuperarContraseniaRequestDTO;
import ar.edu.undec.procoop.backend.dto.request.RestablecerContraseniaRequestDTO;
import ar.edu.undec.procoop.backend.entity.TokenRecuperacion;
import ar.edu.undec.procoop.backend.entity.Usuario;
import ar.edu.undec.procoop.backend.exception.AppException;
import ar.edu.undec.procoop.backend.repository.TokenRecuperacionRepository;
import ar.edu.undec.procoop.backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Servicio de recuperación de contraseña.
 *
 * Flujo:
 *  1. Validar email y número de cuenta
 *  2. Generar token UUID único con expiración de 1 hora
 *  3. Guardar token en BD
 *  4. Enviar email con el link de recuperación
 *
 *  Al restablecer:
 *  1. Validar token (existe, no expiró, no fue usado)
 *  2. Validar que nueva contraseña y confirmación coincidan
 *  3. Actualizar contraseña
 *  4. Marcar token como usado
 *
 * Reglas de negocio:
 *  - Se validan email Y número de cuenta para mayor seguridad
 *  - El token expira en 1 hora
 *  - El token solo puede usarse una vez
 */
@Service
@RequiredArgsConstructor
public class RecuperacionService {

    private final UsuarioRepository usuarioRepository;
    private final TokenRecuperacionRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public void solicitarRecuperacion(RecuperarContraseniaRequestDTO dto) {
        // Validar que el email exista
        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new AppException(
                        "No existe una cuenta con ese email.",
                        HttpStatus.NOT_FOUND));

        // Validar que el número de cuenta coincida
        if (!usuario.getNumeroCuenta().equals(dto.getNumeroCuenta())) {
            throw new AppException(
                    "Los datos ingresados no coinciden.",
                    HttpStatus.BAD_REQUEST);
        }

        // Validar que la cuenta esté activa
        if (!"ACTIVO".equals(usuario.getEstado())) {
            throw new AppException(
                    "La cuenta no está activa.",
                    HttpStatus.FORBIDDEN);
        }

        // Generar token único
        String token = UUID.randomUUID().toString();

        TokenRecuperacion tokenRecuperacion = new TokenRecuperacion();
        tokenRecuperacion.setToken(token);
        tokenRecuperacion.setUsuario(usuario);
        tokenRecuperacion.setFechaCreacion(LocalDateTime.now());
        tokenRecuperacion.setFechaExpiracion(LocalDateTime.now().plusHours(1));
        tokenRecuperacion.setUsado(false);

        tokenRepository.save(tokenRecuperacion);

        // Enviar email
        emailService.enviarEmailRecuperacion(usuario.getEmail(), token);
    }

    public void restablecerContrasenia(RestablecerContraseniaRequestDTO dto) {
        // Buscar token
        TokenRecuperacion tokenRecuperacion = tokenRepository.findByToken(dto.getToken())
                .orElseThrow(() -> new AppException(
                        "El enlace de recuperación no es válido.",
                        HttpStatus.BAD_REQUEST));

        // Validar que no haya expirado
        if (LocalDateTime.now().isAfter(tokenRecuperacion.getFechaExpiracion())) {
            throw new AppException(
                    "El enlace de recuperación ha expirado. Solicitá uno nuevo.",
                    HttpStatus.BAD_REQUEST);
        }

        // Validar que no haya sido usado
        if (tokenRecuperacion.getUsado()) {
            throw new AppException(
                    "Este enlace ya fue utilizado.",
                    HttpStatus.BAD_REQUEST);
        }

        // Validar que las contraseñas coincidan
        if (!dto.getContraseniaNueva().equals(dto.getConfirmacion())) {
            throw new AppException(
                    "La nueva contraseña y la confirmación no coinciden.",
                    HttpStatus.BAD_REQUEST);
        }

        // Actualizar contraseña
        Usuario usuario = tokenRecuperacion.getUsuario();
        usuario.setContrasenia(passwordEncoder.encode(dto.getContraseniaNueva()));
        usuarioRepository.save(usuario);

        // Marcar token como usado
        tokenRecuperacion.setUsado(true);
        tokenRepository.save(tokenRecuperacion);
    }
}