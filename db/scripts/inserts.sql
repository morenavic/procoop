-- ============================================================
-- SCRIPT DE DATOS DE PRUEBA — PROCOOP
-- ============================================================

-- Roles (deben existir previamente, pero los incluimos por si acaso)
INSERT INTO rol (id_rol, nombre) VALUES (1, 'ADMINISTRADOR') ON CONFLICT DO NOTHING;
INSERT INTO rol (id_rol, nombre) VALUES (2, 'CLIENTE') ON CONFLICT DO NOTHING;

-- ============================================================
-- USUARIOS
-- Admin: Admin123! | Clientes: Clientes123
-- ============================================================

INSERT INTO usuario (nombre, email, contrasenia, numero_cuenta, estado, id_rol) VALUES
('Administrador Procoop', 'admin@procoop.com.ar', '$2a$10$eu9h07R38PN2JLT7ubsNGuJ6m0G0niIXdlcb.rqqIKRyzMC9itadm', 'ADM-001', 'ACTIVO', 1);

INSERT INTO usuario (nombre, email, contrasenia, numero_cuenta, estado, id_rol) VALUES
('María González', 'maria.gonzalez@gmail.com', '$2a$10$jXcIWSil391WVfoYllZsduqjGaRnDGPzFMc.xgZ8ysSV3MYG.ED72', 'CLI-001', 'ACTIVO', 2),
('Carlos Rodríguez', 'carlos.rodriguez@gmail.com', '$2a$10$jXcIWSil391WVfoYllZsduqjGaRnDGPzFMc.xgZ8ysSV3MYG.ED72', 'CLI-002', 'ACTIVO', 2),
('Ana Martínez', 'ana.martinez@gmail.com', '$2a$10$jXcIWSil391WVfoYllZsduqjGaRnDGPzFMc.xgZ8ysSV3MYG.ED72', 'CLI-003', 'ACTIVO', 2),
('Luis Fernández', 'luis.fernandez@gmail.com', '$2a$10$jXcIWSil391WVfoYllZsduqjGaRnDGPzFMc.xgZ8ysSV3MYG.ED72', 'CLI-004', 'ACTIVO', 2),
('Laura Pérez', 'laura.perez@gmail.com', '$2a$10$jXcIWSil391WVfoYllZsduqjGaRnDGPzFMc.xgZ8ysSV3MYG.ED72', 'CLI-005', 'INACTIVO', 2),
('Diego Sánchez', 'diego.sanchez@gmail.com', '$2a$10$jXcIWSil391WVfoYllZsduqjGaRnDGPzFMc.xgZ8ysSV3MYG.ED72', 'CLI-006', 'PENDIENTE', 2),
('Sofía López', 'sofia.lopez@gmail.com', '$2a$10$jXcIWSil391WVfoYllZsduqjGaRnDGPzFMc.xgZ8ysSV3MYG.ED72', 'CLI-007', 'ACTIVO', 2);

-- ============================================================
-- PRODUCTOS
-- ============================================================

INSERT INTO producto (titulo, subtitulo, descripcion, imagen) VALUES
(
  'ProCoop Gestión',
  'Sistema de Gestión Integral para Cooperativas',
  'Sistema de Gestión Integral desarrollado exclusivamente para Cooperativas y empresas de Servicios. Incorpora todas las funcionalidades para gestión de servicios tales como Energía Eléctrica, Gas Natural, Telefonía, Agua, Internet, TV y Servicios Sociales. Es un sistema flexible y configurable que se adapta a las principales necesidades de cada organización.',
  NULL
),
(
  'ProCoop P-Móvil',
  'Toma de estados de medidores desde dispositivos móviles',
  'Aplicación móvil para toma de estados de medidores con dispositivos de lectura de bajo costo. Permite la captura de lecturas en campo y su integración directa con el sistema de gestión ProCoop, reduciendo errores y optimizando los tiempos de relevamiento.',
  NULL
),
(
  'ProCoop 3S',
  'Soporte permanente para sistemas de gestión de servicios',
  'El apoyo permanente que necesita todo usuario de Sistemas de Gestión de Servicios Públicos. Brinda asistencia continua, actualizaciones y acompañamiento técnico para garantizar el correcto funcionamiento del sistema en todo momento.',
  NULL
),
(
  'ProCoop POS',
  'Punto de venta fiscal integrado',
  'Complemento ideal de ProCoop Gestión, diseñado exclusivamente para clientes que necesiten incorporar Puntos de Ventas Fiscales en su empresa. Se integra e interrelaciona con todos los módulos de ProCoop Gestión.',
  NULL
),
(
  'ProCoop Web',
  'Autogestión de servicios para usuarios en línea',
  'Servicio web estándar configurable que posibilita mostrar contenido de ProCoop Gestión en un sitio web al que pueden acceder los usuarios de servicios públicos para imprimir facturas, consultar deudas, consumos y generar reclamos desde cualquier dispositivo.',
  NULL
),
(
  'ProCoop SMS',
  'Comunicación masiva con asociados por mensajería',
  'Complemento de ProCoop Gestión que permite el envío de mensajes de texto a los celulares de asociados, clientes o usuarios. Permite mensajes personalizados con datos de factura, avisos de corte de servicio, citaciones y promociones.',
  NULL
),
(
  'ProCoop Turnero',
  'Sistema de gestión de turnos y atención al público',
  'Facilita la atención de personas en espera mediante el llamado secuencial por turnos. Permite la definición de múltiples mesas de atención, boxes y cajas. Genera estadísticas de tiempo de espera y cantidad de personas atendidas por puesto.',
  NULL
);

-- ============================================================
-- SERVICIOS
-- ============================================================

INSERT INTO servicio (titulo, descripcion) VALUES
(
  'Consultoría, Análisis e Implementación de Sistemas',
  'Brindamos el servicio de relevamiento y análisis para la implementación de soluciones propias, trabajando conjuntamente con el cliente para identificar necesidades, proponer mejoras y garantizar una implementación exitosa.'
),
(
  'Soluciones llave en mano con resultados garantizados',
  'Acompañamos y asistimos a nuestros clientes con nuestro equipo de profesionales durante todo el proyecto, desde el relevamiento de necesidades hasta la post-implementación. El alcance y las características de asistencia se ajustan a la necesidad de cada cliente.'
),
(
  'Capacitación y entrenamiento',
  'Realizamos capacitaciones a medida de las necesidades de nuestros clientes en el uso de nuestros productos. Las capacitaciones se realizan in company, en nuestras oficinas o de manera remota, con material de apoyo para autocapacitación.'
),
(
  'Metodología de implementación',
  'A lo largo de todos estos años definimos una metodología que permite asegurar la implementación exitosa de nuestros proyectos, cubriendo las etapas de análisis, definición, migración, parametrización, prueba, puesta en marcha y acompañamiento.'
),
(
  'Migración de sistemas',
  'Analizamos la factibilidad técnica y definimos los procesos de migración del sistema en uso al nuevo sistema ProCoop, garantizando la integridad de los datos históricos y la continuidad operativa durante el proceso de transición.'
),
(
  'Soporte y acompañamiento post-implementación',
  'Acompañamos al cliente en todas las situaciones necesarias, tanto para los procesos operativos como en la definición y adaptación de nuevos procesos. Brindamos soporte técnico continuo para garantizar el correcto funcionamiento del sistema.'
),
(
  'Desarrollo de soluciones a medida',
  'Desarrollamos funcionalidades específicas adaptadas a las necesidades particulares de cada organización, integrando las soluciones con los sistemas existentes y asegurando la escalabilidad y mantenimiento a largo plazo.'
);

-- ============================================================
-- NOVEDADES
-- ============================================================

INSERT INTO novedad (titulo, descripcion, fecha, imagen, tipo) VALUES
(
  'Lanzamiento de ProCoop Gestión versión 5.0',
  'Con gran satisfacción anunciamos el lanzamiento de la nueva versión 5.0 de ProCoop Gestión. Esta actualización incorpora mejoras significativas en el módulo de facturación electrónica, nuevas funcionalidades para la gestión de cobranzas y una interfaz renovada que optimiza la experiencia de uso.',
  '2026-05-15',
  NULL,
  'NOTICIA'
),
(
  'Asamblea General Ordinaria 2026',
  'Convocamos a todos los socios a participar de la Asamblea General Ordinaria que se realizará el próximo 20 de junio de 2026 en nuestras instalaciones de Córdoba. En esta oportunidad se tratarán los estados contables del ejercicio, la memoria y balance, y la elección de autoridades.',
  '2026-05-20',
  NULL,
  'EVENTO'
),
(
  'Nuevas funcionalidades en ProCoop Web',
  'Informamos a todos nuestros clientes que hemos incorporado nuevas funcionalidades al módulo ProCoop Web. Los usuarios ahora pueden gestionar sus reclamos en línea, visualizar el historial de pagos y descargar comprobantes de los últimos 12 meses desde cualquier dispositivo.',
  '2026-04-10',
  NULL,
  'NOTICIA'
),
(
  'Jornada de capacitación en Monte Maíz',
  'Realizaremos una jornada de capacitación intensiva en nuestra sede de Monte Maíz el 15 de julio de 2026. La capacitación está orientada a usuarios del módulo de facturación y cobranzas de ProCoop Gestión. Los interesados deben inscribirse antes del 30 de junio.',
  '2026-04-25',
  NULL,
  'EVENTO'
),
(
  'Incorporamos soporte para factura electrónica AFIP',
  'ProCoop Gestión ahora incorpora soporte completo para factura electrónica según las últimas resoluciones de AFIP. Todos nuestros clientes con contratos de soporte activos recibirán la actualización de forma gratuita durante el mes de mayo.',
  '2026-03-18',
  NULL,
  'NOTICIA'
),
(
  'Encuentro anual de cooperativas del NOA',
  'Procoop participará como expositor en el Encuentro Anual de Cooperativas del NOA que se celebrará en San Salvador de Jujuy los días 8 y 9 de agosto de 2026. Presentaremos las últimas novedades de nuestra suite de productos.',
  '2026-03-05',
  NULL,
  'EVENTO'
),
(
  'Acuerdo de colaboración con nueva cooperativa eléctrica',
  'Nos complace anunciar la firma de un nuevo acuerdo de implementación con una cooperativa de distribución eléctrica de la provincia de Córdoba. Este proyecto incluye la implementación completa de ProCoop Gestión con los módulos de facturación, cobranzas y gestión técnica.',
  '2026-02-12',
  NULL,
  'NOTICIA'
);

-- ============================================================
-- DOCUMENTOS
-- ============================================================

INSERT INTO documento (nombre, descripcion, tipo, archivo) VALUES
(
  'Manual de usuario ProCoop Gestión',
  'Guía completa para el uso del sistema ProCoop Gestión. Cubre todos los módulos disponibles: asociados, servicios, consumos, facturación, ventas y cobranzas.',
  'MANUAL',
  'documentos/manual-procoop-gestion.pdf'
),
(
  'Guía de instalación y configuración inicial',
  'Paso a paso para la instalación del sistema ProCoop en entornos Windows Server. Incluye requisitos mínimos de hardware, configuración de base de datos y puesta en marcha.',
  'GUIA',
  'documentos/guia-instalacion.pdf'
),
(
  'Manual de módulo facturación electrónica',
  'Documentación completa del módulo de facturación electrónica integrado con AFIP. Incluye configuración de certificados digitales, tipos de comprobantes y resolución de errores frecuentes.',
  'MANUAL',
  'documentos/manual-facturacion-electronica.pdf'
),
(
  'Guía de migración de datos',
  'Procedimiento detallado para la migración de datos desde sistemas anteriores a ProCoop Gestión. Incluye formatos de importación, validaciones y verificación de integridad.',
  'GUIA',
  'documentos/guia-migracion-datos.pdf'
),
(
  'Reglamento interno de socios',
  'Documento oficial que establece los derechos y obligaciones de los socios de la cooperativa, así como los procedimientos para altas, bajas y modificaciones de servicios.',
  'OTRO',
  'documentos/reglamento-interno.pdf'
),
(
  'Manual de ProCoop P-Móvil',
  'Guía de uso de la aplicación móvil para la toma de estados de medidores. Incluye configuración del dispositivo, sincronización con el sistema central y resolución de problemas comunes.',
  'MANUAL',
  'documentos/manual-pmovil.pdf'
),
(
  'Política de privacidad y tratamiento de datos',
  'Documento que describe cómo Procoop recopila, utiliza y protege los datos personales de sus asociados y usuarios, en cumplimiento con la Ley 25.326 de Protección de Datos Personales.',
  'OTRO',
  'documentos/politica-privacidad.pdf'
);