package ar.edu.undec.procoop.backend.service;

import ar.edu.undec.procoop.backend.dto.request.CambiarContraseniaRequestDTO;
import ar.edu.undec.procoop.backend.dto.response.PerfilResponseDTO;
import ar.edu.undec.procoop.backend.entity.Usuario;
import ar.edu.undec.procoop.backend.exception.AppException;
import ar.edu.undec.procoop.backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Servicio del perfil del cliente autenticado.
 *
 * Responsabilidades:
 *  - Obtener datos del perfil
 *  - Actualizar foto de perfil
 *  - Cambiar contraseña validando la actual
 *
 * Reglas de negocio:
 *  - La contraseña actual debe coincidir antes de cambiarla
 *  - La nueva contraseña y la confirmación deben ser iguales
 *  - La foto de perfil es opcional y se almacena en disco
 */
@Service
@RequiredArgsConstructor
public class PerfilService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final ArchivoService archivoService;

    public PerfilResponseDTO obtenerPerfil(String email) {
        Usuario usuario = buscarPorEmail(email);
        return mapearAResponse(usuario);
    }

    public PerfilResponseDTO actualizarFoto(String email, MultipartFile imagen) {
        Usuario usuario = buscarPorEmail(email);

        // Eliminar foto anterior si existe
        if (usuario.getImagen() != null) {
            archivoService.eliminar(usuario.getImagen());
        }

        usuario.setImagen(archivoService.guardarImagen(imagen, "perfiles"));
        return mapearAResponse(usuarioRepository.save(usuario));
    }

    public void cambiarContrasenia(String email, CambiarContraseniaRequestDTO dto) {
        Usuario usuario = buscarPorEmail(email);

        if (!passwordEncoder.matches(dto.getContraseniaActual(), usuario.getContrasenia())) {
            throw new AppException(
                    "La contraseña actual es incorrecta", HttpStatus.BAD_REQUEST);
        }

        if (!dto.getContraseniaNueva().equals(dto.getConfirmacion())) {
            throw new AppException(
                    "La nueva contraseña y la confirmación no coinciden", HttpStatus.BAD_REQUEST);
        }

        usuario.setContrasenia(passwordEncoder.encode(dto.getContraseniaNueva()));
        usuarioRepository.save(usuario);
    }

    private Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(
                        "Usuario no encontrado", HttpStatus.NOT_FOUND));
    }

    private PerfilResponseDTO mapearAResponse(Usuario usuario) {
        String imagenUrl = usuario.getImagen() != null
                ? "http://localhost:8080/uploads/" + usuario.getImagen()
                : null;

        return new PerfilResponseDTO(
                usuario.getIdUsuario(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getNumeroCuenta(),
                imagenUrl
        );
    }
}