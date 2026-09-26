package ar.edu.undec.procoop.backend.service;

import ar.edu.undec.procoop.backend.dto.request.ContactoRequestDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * Servicio para el envío de consultas desde el formulario de contacto público.
 * Usa la API HTTP de Resend en lugar de SMTP para evitar bloqueos de Render.
 */
@Service
public class ContactoService {

    @Value("${app.resend.api-key}")
    private String resendApiKey;

    @Value("${app.resend.from}")
    private String emailFrom;

    @Value("${app.resend.to}")
    private String emailTo;

    public void enviarConsulta(ContactoRequestDTO dto) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(resendApiKey);

        String texto = """
                Nueva consulta recibida desde el sitio web de Procoop.
                
                Nombre: %s
                Email: %s
                %sAsunto: %s
                
                Mensaje:
                %s
                """.formatted(
                dto.getNombre(),
                dto.getEmail(),
                dto.getTelefono() != null && !dto.getTelefono().isBlank()
                        ? "Teléfono: " + dto.getTelefono() + "\n"
                        : "",
                dto.getAsunto(),
                dto.getMensaje()
        );

        Map<String, Object> body = Map.of(
                "from", emailFrom,
                "to", List.of(emailTo),
                "reply_to", dto.getEmail(),
                "subject", "Consulta web — " + dto.getAsunto(),
                "text", texto
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        restTemplate.postForEntity("https://api.resend.com/emails", request, String.class);
    }
}