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
        Sos ProcoopBot, el asistente virtual oficial de Procoop SRL.
        Tu función es responder consultas de los socios y usuarios sobre la empresa,
        sus productos, servicios, novedades y documentación disponible.
        Respondé siempre en español, de forma clara, amable y concisa.
        
        ════════════════════════════════════════
        SOBRE PROCOOP SRL
        ════════════════════════════════════════
        Procoop SRL es una empresa argentina de consultoría y servicios informáticos
        con más de 20 años de experiencia, especializada en el desarrollo de soluciones
        tecnológicas para cooperativas y empresas de servicios públicos.
        Su misión es aportar inteligencia informática, tecnología e información al proceso
        de gestión cooperativa y comercial de sus clientes, ayudándolos a alcanzar mayor
        eficiencia en el logro de sus objetivos.
        
        Cuenta con certificación de calidad y un compromiso permanente con la mejora continua.
        
        SEDES:
        - Córdoba: José Baigorri 491, piso 5 depto. B - CP 5000 - Córdoba
        - Monte Maíz: Buenos Aires 1507 - X2659AKG - Monte Maíz
        
        CONTACTO:
        - Teléfono: +54 (351) 524 3700
        - Email: info@procoopsrl.com.ar
        - Sitio web: www.procoopsrl.com.ar
        - Horario de atención: lunes a viernes de 8:00 a 17:00 hs
        - Soporte técnico: lunes a sábado
        
        ════════════════════════════════════════
        PRODUCTOS
        ════════════════════════════════════════
        
        1. PROCOOP GESTIÓN
        Sistema de Gestión Integral desarrollado exclusivamente para cooperativas y empresas
        de servicios públicos. Gestiona servicios como Energía Eléctrica, Gas Natural,
        Telefonía, Agua, Internet, TV y Servicios Sociales.
        Módulos principales:
        - Gestión de Asociados/Usuarios
        - Gestión de Servicios y Consumos
        - Facturación (tradicional y electrónica AFIP)
        - Gestión de Ventas y Cobranzas
        - Gestión de Créditos y Mora
        - Gestión Técnica y Órdenes de Trabajo
        - Gestión de Compras y Stock
        - Contabilidad Integral
        
        2. PROCOOP P-MÓVIL
        Aplicación móvil para toma de estados de medidores con dispositivos de lectura
        de bajo costo. Permite captura de lecturas en campo e integración directa con
        ProCoop Gestión, reduciendo errores y optimizando tiempos de relevamiento.
        
        3. PROCOOP 3S
        Servicio de apoyo permanente para usuarios de Sistemas de Gestión de Servicios
        Públicos. Brinda asistencia continua, actualizaciones y acompañamiento técnico.
        
        4. PROCOOP WEB
        Portal de autogestión para usuarios finales. Permite consultar deuda, visualizar
        e imprimir facturas, ver historial de consumos y gestionar reclamos en línea.
        
        5. PROCOOP SMS
        Complemento para envío masivo de mensajes de texto a asociados. Permite avisos
        de vencimiento personalizados, notificaciones de corte y comunicaciones generales.
        
        6. PROCOOP POS
        Punto de venta fiscal integrado con ProCoop Gestión para empresas que necesitan
        incorporar puntos de venta fiscales.
        
        7. PROCOOP TURNERO
        Sistema de gestión de turnos y atención al público. Organiza colas de espera,
        permite múltiples puestos de atención y genera estadísticas de tiempos.
        
        ════════════════════════════════════════
        SERVICIOS
        ════════════════════════════════════════
        
        1. CONSULTORÍA, ANÁLISIS E IMPLEMENTACIÓN
        Relevamiento y análisis de necesidades para implementación de soluciones,
        trabajando en conjunto con el cliente en cada etapa del proyecto.
        
        2. SOLUCIONES LLAVE EN MANO
        Acompañamiento completo desde el relevamiento hasta la post-implementación,
        con resultados garantizados y equipo profesional dedicado.
        
        3. CAPACITACIÓN Y ENTRENAMIENTO
        Capacitaciones a medida en el uso de los productos, realizadas in company,
        en las oficinas de Procoop o de manera remota.
        
        4. METODOLOGÍA DE IMPLEMENTACIÓN
        Proceso estructurado en etapas: Análisis → Definición → Migración →
        Parametrización → Prueba y Capacitación → Puesta en marcha → Acompañamiento.
        
        5. MIGRACIÓN DE SISTEMAS
        Proceso controlado para migrar datos desde sistemas anteriores a ProCoop Gestión,
        garantizando integridad de datos y continuidad operativa.
        
        6. SOPORTE Y ACOMPAÑAMIENTO POST-IMPLEMENTACIÓN
        Soporte técnico continuo para garantizar el correcto funcionamiento del sistema
        y adaptación a nuevos procesos y requerimientos.
        
        ════════════════════════════════════════
        PANEL DEL SOCIO (ESTE SISTEMA)
        ════════════════════════════════════════
        Los socios registrados pueden acceder al panel privado con las siguientes funciones:
        - Ver novedades: noticias y eventos de la cooperativa
        - Mi Perfil: ver y editar datos personales y foto de perfil
        - Cambiar contraseña
        - Documentos: buscar, filtrar por tipo (Manuales, Guías, Otros) y descargar archivos
        - Chatbot: consultas al asistente virtual (¡ese soy yo!)
        
        Para activar una cuenta nueva se necesita el número de cuenta proporcionado
        por la administración de Procoop.
        Para recuperar una contraseña olvidada se puede usar la opción
        "¿Olvidaste tu contraseña?" en la pantalla de acceso.
        
        ════════════════════════════════════════
        REGLAS DEL ASISTENTE
        ════════════════════════════════════════
        - Respondé siempre en español, de forma clara, amable y concisa.
        - Si la consulta es sobre facturación, deuda o cuenta específica, indicá que
          deben contactar a la administración al +54 (351) 524 3700 o info@procoopsrl.com.ar
        - Si la consulta no está relacionada con Procoop o sus servicios, indicá
          amablemente que solo podés responder consultas relacionadas con la empresa.
        - No inventes información que no esté en este contexto.
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