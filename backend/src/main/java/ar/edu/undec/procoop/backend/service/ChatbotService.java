package ar.edu.undec.procoop.backend.service;

import ar.edu.undec.procoop.backend.dto.request.ChatRequestDTO;
import ar.edu.undec.procoop.backend.dto.response.ChatResponseDTO;
import ar.edu.undec.procoop.backend.exception.AppException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

/**
 * Servicio de integración con la API de OpenAI (ChatGPT).
 * Usa gpt-4o-mini que es el modelo más económico y eficiente.
 */
@Service
public class ChatbotService {

    @Value("${app.openai.api-key}")
    private String apiKey;

    @Value("${app.openai.url}")
    private String openaiUrl;

    @Value("${app.openai.model}")
    private String modelo;

    private static final String CONTEXTO_PROCOOP = """
            Sos un asistente virtual de Procoop, una cooperativa de servicios de telecomunicaciones.
            Tu función es responder consultas de los socios sobre los servicios, productos, documentos
            y novedades de la cooperativa.
            
            Información sobre Procoop:
            - Es una cooperativa de servicios de telecomunicaciones
            - Ofrece servicios de internet hogar, internet empresas, telefonía fija, televisión digital
            - Los socios pueden acceder a documentos, manuales y guías desde su panel
            - Las novedades incluyen noticias y eventos de la cooperativa
            - Para consultas específicas sobre facturación o cuenta, deben contactar a la administración
            - El soporte técnico está disponible de lunes a sábado
            
            Respondé siempre en español, de forma clara, amable y concisa.
            Si la consulta no está relacionada con Procoop o sus servicios, indicá amablemente
            que solo podés responder consultas relacionadas con la cooperativa.
            """;

    public ChatResponseDTO consultar(ChatRequestDTO dto) {
        String body = """
                {
                  "model": "%s",
                  "messages": [
                    { "role": "system", "content": %s },
                    { "role": "user", "content": %s }
                  ]
                }
                """.formatted(modelo, toJsonString(CONTEXTO_PROCOOP), toJsonString(dto.getConsulta()));

        try {
            RestClient client = RestClient.create();

            String response = client.post()
                    .uri(openaiUrl)
                    .header("Authorization", "Bearer " + apiKey)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(body)
                    .retrieve()
                    .body(String.class);

            System.out.println("=== RESPUESTA OPENAI ===");
            System.out.println(response);
            System.out.println("========================");

            String texto = extraerTexto(response);
            return new ChatResponseDTO(texto);

        } catch (Exception e) {
            System.out.println("=== ERROR OPENAI: " + e.getMessage() + " ===");
            throw new AppException(
                    "No se pudo procesar la consulta. Intentá nuevamente.",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String toJsonString(String texto) {
        return "\"" + texto
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t")
                + "\"";
    }

    /**
     * Extrae el texto de la respuesta JSON de OpenAI.
     * Busca choices[0].message.content
     */
    private String extraerTexto(String json) {
        try {
            String buscar = "\"content\": \"";
            int inicio = json.indexOf(buscar);
            if (inicio == -1) {
                buscar = "\"content\":\"";
                inicio = json.indexOf(buscar);
            }
            if (inicio == -1) throw new RuntimeException("Campo content no encontrado");

            inicio += buscar.length();
            StringBuilder resultado = new StringBuilder();
            boolean escape = false;

            for (int i = inicio; i < json.length(); i++) {
                char c = json.charAt(i);
                if (escape) {
                    switch (c) {
                        case 'n' -> resultado.append('\n');
                        case 't' -> resultado.append('\t');
                        case '"' -> resultado.append('"');
                        case '\\' -> resultado.append('\\');
                        default -> resultado.append(c);
                    }
                    escape = false;
                } else if (c == '\\') {
                    escape = true;
                } else if (c == '"') {
                    break;
                } else {
                    resultado.append(c);
                }
            }

            return resultado.toString();
        } catch (Exception e) {
            return "No pude procesar la respuesta. Intentá nuevamente.";
        }
    }
}