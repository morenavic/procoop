package ar.edu.undec.procoop.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Servicio para el envío de emails.
 * Usa JavaMailSender configurado con Gmail SMTP.
 */
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String emailOrigen;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    /**
     * Envía el email de recuperación de contraseña con el link temporal.
     *
     * @param destinatario email del usuario
     * @param token        token único generado para esta solicitud
     */
    public void enviarEmailRecuperacion(String destinatario, String token) {
        String link = frontendUrl + "/acceso/restablecer?token=" + token;

        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setFrom(emailOrigen);
        mensaje.setTo(destinatario);
        mensaje.setSubject("Recuperación de contraseña — Procoop");
        mensaje.setText(
                "Hola,\n\n" +
                        "Recibimos una solicitud para restablecer la contraseña de tu cuenta en Procoop.\n\n" +
                        "Hacé click en el siguiente enlace para crear una nueva contraseña:\n\n" +
                        link + "\n\n" +
                        "Este enlace expira en 1 hora.\n\n" +
                        "Si no solicitaste este cambio, podés ignorar este email.\n\n" +
                        "Procoop"
        );

        mailSender.send(mensaje);
    }
}