package ar.edu.undec.procoop.backend.service;

import ar.edu.undec.procoop.backend.dto.request.ContactoRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Servicio para el envío de consultas desde el formulario de contacto público.
 * Usa el mismo JavaMailSender configurado con Gmail SMTP.
 * El email de destino es el mismo correo configurado como remitente.
 */
@Service
@RequiredArgsConstructor
public class ContactoService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String emailDestino;

    public void enviarConsulta(ContactoRequestDTO dto) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setFrom(emailDestino);
        mensaje.setTo(emailDestino);
        mensaje.setReplyTo(dto.getEmail());
        mensaje.setSubject("Consulta web — " + dto.getAsunto());
        mensaje.setText(
                "Nueva consulta recibida desde el sitio web de Procoop.\n\n" +
                        "Nombre: " + dto.getNombre() + "\n" +
                        "Email: " + dto.getEmail() + "\n" +
                        (dto.getTelefono() != null && !dto.getTelefono().isBlank()
                                ? "Teléfono: " + dto.getTelefono() + "\n"
                                : "") +
                        "Asunto: " + dto.getAsunto() + "\n\n" +
                        "Mensaje:\n" + dto.getMensaje()
        );
        mailSender.send(mensaje);
    }
}