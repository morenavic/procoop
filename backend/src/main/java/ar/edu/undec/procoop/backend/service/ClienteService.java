package ar.edu.undec.procoop.backend.service;

import ar.edu.undec.procoop.backend.dto.request.ClienteActivacionDTO;
import ar.edu.undec.procoop.backend.dto.request.ClienteRequestDTO;
import ar.edu.undec.procoop.backend.dto.response.ClienteResponseDTO;
import ar.edu.undec.procoop.backend.entity.Rol;
import ar.edu.undec.procoop.backend.entity.Usuario;
import ar.edu.undec.procoop.backend.exception.AppException;
import ar.edu.undec.procoop.backend.repository.RolRepository;
import ar.edu.undec.procoop.backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio de gestión de clientes desde el panel de administración.
 *
 * Responsabilidades:
 *  - Listar todos los clientes
 *  - Crear cliente con estado PENDIENTE
 *  - Editar datos básicos del cliente
 *  - Cambiar estado: ACTIVO / INACTIVO / PENDIENTE
 *  - Eliminar cliente
 *
 * Reglas de negocio:
 *  - El email debe ser único en el sistema
 *  - El número de cuenta debe ser único en el sistema
 *  - Al crear un cliente, el estado inicial es siempre PENDIENTE
 *  - La contraseña inicial es el número de cuenta (el cliente la cambia al activar)
 *  - Solo se listan usuarios con rol CLIENTE
 */
@Service
@RequiredArgsConstructor
public class ClienteService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public List<ClienteResponseDTO> listar() {
        return usuarioRepository.findByRolNombre("CLIENTE")
                .stream()
                .map(this::mapearAResponse)
                .toList();
    }

    public ClienteResponseDTO obtenerPorId(Integer id) {
        return mapearAResponse(buscarClientePorId(id));
    }

    public ClienteResponseDTO crear(ClienteRequestDTO dto) {
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new AppException("El email ya está registrado", HttpStatus.CONFLICT);
        }
        if (usuarioRepository.existsByNumeroCuenta(dto.getNumeroCuenta())) {
            throw new AppException("El número de cuenta ya está registrado", HttpStatus.CONFLICT);
        }

        Rol rolCliente = rolRepository.findByNombre("CLIENTE")
                .orElseThrow(() -> new AppException(
                        "Rol CLIENTE no encontrado", HttpStatus.INTERNAL_SERVER_ERROR));

        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());
        usuario.setNumeroCuenta(dto.getNumeroCuenta());
        usuario.setContrasenia(passwordEncoder.encode(dto.getNumeroCuenta()));
        usuario.setEstado("PENDIENTE");
        usuario.setRol(rolCliente);

        return mapearAResponse(usuarioRepository.save(usuario));
    }

    public ClienteResponseDTO editar(Integer id, ClienteRequestDTO dto) {
        Usuario usuario = buscarClientePorId(id);

        if (!usuario.getEmail().equals(dto.getEmail()) &&
                usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new AppException("El email ya está registrado", HttpStatus.CONFLICT);
        }

        if (!usuario.getNumeroCuenta().equals(dto.getNumeroCuenta()) &&
                usuarioRepository.existsByNumeroCuenta(dto.getNumeroCuenta())) {
            throw new AppException("El número de cuenta ya está registrado", HttpStatus.CONFLICT);
        }

        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());
        usuario.setNumeroCuenta(dto.getNumeroCuenta());

        return mapearAResponse(usuarioRepository.save(usuario));
    }

    public ClienteResponseDTO cambiarEstado(Integer id, ClienteActivacionDTO dto) {
        Usuario usuario = buscarClientePorId(id);
        usuario.setEstado(dto.getEstado());
        return mapearAResponse(usuarioRepository.save(usuario));
    }

    public void eliminar(Integer id) {
        usuarioRepository.delete(buscarClientePorId(id));
    }

    private Usuario buscarClientePorId(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new AppException(
                        "Cliente no encontrado", HttpStatus.NOT_FOUND));

        if (!"CLIENTE".equals(usuario.getRol().getNombre())) {
            throw new AppException("El usuario no es un cliente", HttpStatus.BAD_REQUEST);
        }

        return usuario;
    }

    private ClienteResponseDTO mapearAResponse(Usuario usuario) {
        return new ClienteResponseDTO(
                usuario.getIdUsuario(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getNumeroCuenta(),
                usuario.getEstado(),
                usuario.getImagen()
        );
    }
}