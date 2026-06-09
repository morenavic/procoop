package ar.edu.undec.procoop.backend.service;

import ar.edu.undec.procoop.backend.dto.request.LoginRequestDTO;
import ar.edu.undec.procoop.backend.dto.response.LoginResponseDTO;
import ar.edu.undec.procoop.backend.entity.Usuario;
import ar.edu.undec.procoop.backend.exception.AppException;
import ar.edu.undec.procoop.backend.repository.UsuarioRepository;
import ar.edu.undec.procoop.backend.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Servicio de autenticación del sistema.
 *
 * Responsabilidades:
 *  - Validar credenciales del usuario
 *  - Verificar estado de la cuenta (debe estar ACTIVO)
 *  - Generar token JWT al autenticar exitosamente
 *
 * Reglas de negocio:
 *  - Un usuario con estado PENDIENTE o INACTIVO no puede iniciar sesión
 *  - Las credenciales incorrectas devuelven un mensaje genérico
 *    para no revelar si el email existe o no
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public LoginResponseDTO iniciarSesion(LoginRequestDTO request) {
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(
                        "Credenciales inválidas", HttpStatus.UNAUTHORIZED));

        // DEBUG TEMPORAL
        System.out.println("=== DEBUG LOGIN ===");
        System.out.println("Email: " + request.getEmail());
        System.out.println("Contraseña ingresada: [" + request.getContrasenia() + "]");
        System.out.println("Hash en BD: [" + usuario.getContrasenia() + "]");
        System.out.println("Matches: " + passwordEncoder.matches(request.getContrasenia(), usuario.getContrasenia()));
        System.out.println("==================");

        if (!passwordEncoder.matches(request.getContrasenia(), usuario.getContrasenia())) {
            throw new AppException("Credenciales inválidas", HttpStatus.UNAUTHORIZED);
        }

        if (!"ACTIVO".equals(usuario.getEstado())) {
            throw new AppException(
                    "La cuenta no está activa. Contacte al administrador.",
                    HttpStatus.FORBIDDEN);
        }

        String token = jwtUtil.generarToken(
                usuario.getEmail(),
                usuario.getRol().getNombre(),
                usuario.getIdUsuario()
        );

        return new LoginResponseDTO(
                token,
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getRol().getNombre()
        );
    }
}