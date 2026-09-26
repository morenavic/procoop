package ar.edu.undec.procoop.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * Servicio para el envío de emails usando la API HTTP de Resend.
 * Reemplaza JavaMailSender para evitar bloqueos SMTP en Render.
 */
@Service
public class EmailService {

    @Value("${app.resend.api-key}")
    private String resendApiKey;

    @Value("${app.resend.from}")
    private String emailFrom;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    public void enviarEmailRecuperacion(String destinatario, String token) {
        String link = frontendUrl + "/acceso/restablecer?token=" + token;

        String texto = """
                Hola,
                
                Recibimos una solicitud para restablecer la contraseña de tu cuenta en Procoop.
                
                Hacé click en el siguiente enlace para crear una nueva contraseña:
                
                %s
                
                Este enlace expira en 1 hora.
                
                Si no solicitaste este cambio, podés ignorar este email.
                
                Procoop
                """.formatted(link);

        enviar(destinatario, "Recuperación de contraseña — Procoop", texto);
    }

    private void enviar(String destinatario, String asunto, String texto) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(resendApiKey);

        Map<String, Object> body = Map.of(
                "from", emailFrom,
                "to", List.of(destinatario),
                "subject", asunto,
                "text", texto
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        restTemplate.postForEntity("https://api.resend.com/emails", request, String.class);
    }
}