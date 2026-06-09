package ar.edu.undec.procoop.backend.service;

import ar.edu.undec.procoop.backend.dto.request.ActivacionRequestDTO;
import ar.edu.undec.procoop.backend.entity.Usuario;
import ar.edu.undec.procoop.backend.exception.AppException;
import ar.edu.undec.procoop.backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Servicio de activación de cuenta del usuario cliente.
 *
 * Flujo:
 *  1. Buscar usuario por numeroCuenta
 *  2. Verificar que el estado sea PENDIENTE
 *  3. Actualizar solo la contraseña
 *  4. Cambiar estado a ACTIVO
 *
 * Reglas de negocio:
 *  - Solo se valida el número de cuenta
 *  - El nombre y email los cargó el admin — no se modifican al activar
 *  - El usuario puede modificar sus datos después desde su perfil
 */
@Service
@RequiredArgsConstructor
public class ActivacionService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public void activarCuenta(ActivacionRequestDTO dto) {
        Usuario usuario = usuarioRepository.findByNumeroCuenta(dto.getNumeroCuenta())
                .orElseThrow(() -> new AppException(
                        "El número de cuenta no existe en el sistema. " +
                                "Verificá el dato o contactá al administrador.",
                        HttpStatus.NOT_FOUND));

        if (!"PENDIENTE".equals(usuario.getEstado())) {
            throw new AppException(
                    "Esta cuenta ya fue activada o está inactiva.",
                    HttpStatus.CONFLICT);
        }

        // Solo actualizar contraseña y activar — nombre y email los cargó el admin
        usuario.setContrasenia(passwordEncoder.encode(dto.getContrasenia()));
        usuario.setEstado("ACTIVO");

        usuarioRepository.save(usuario);
    }
}