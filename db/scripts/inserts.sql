-- ============================================================
-- SEED: rol
-- ============================================================
INSERT INTO rol (nombre) VALUES
('ADMIN'),
('CLIENTE');


-- ============================================================
-- SEED: usuario (10 registros)
-- ============================================================
INSERT INTO usuario (nombre, email, contrasenia, numero_cuenta, estado, id_rol, imagen)
VALUES
('María González',    'maria.gonzalez@gmail.com',       '$2a$10$jvNcGfx1I.Y6FmT2fP6NnuIX4OsnnBJKk7wkO17VlnbZulhTPvAqC', '001001', 'ACTIVO',    (SELECT id_rol FROM rol WHERE nombre = 'CLIENTE'), NULL),
('Carlos Rodríguez',  'carlos.rodriguez@hotmail.com',    '$2a$10$jvNcGfx1I.Y6FmT2fP6NnuIX4OsnnBJKk7wkO17VlnbZulhTPvAqC', '001002', 'ACTIVO',    (SELECT id_rol FROM rol WHERE nombre = 'CLIENTE'), NULL),
('Ana Martínez',      'ana.martinez@yahoo.com',           '$2a$10$jvNcGfx1I.Y6FmT2fP6NnuIX4OsnnBJKk7wkO17VlnbZulhTPvAqC', '001003', 'PENDIENTE', (SELECT id_rol FROM rol WHERE nombre = 'CLIENTE'), NULL),
('Luis Fernández',    'luis.fernandez@gmail.com',        '$2a$10$jvNcGfx1I.Y6FmT2fP6NnuIX4OsnnBJKk7wkO17VlnbZulhTPvAqC', '001004', 'PENDIENTE', (SELECT id_rol FROM rol WHERE nombre = 'CLIENTE'), NULL),
('Laura Pérez',       'laura.perez@outlook.com',          '$2a$10$jvNcGfx1I.Y6FmT2fP6NnuIX4OsnnBJKk7wkO17VlnbZulhTPvAqC', '001005', 'ACTIVO',    (SELECT id_rol FROM rol WHERE nombre = 'CLIENTE'), NULL),
('Diego Sánchez',     'diego.sanchez@gmail.com',          '$2a$10$jvNcGfx1I.Y6FmT2fP6NnuIX4OsnnBJKk7wkO17VlnbZulhTPvAqC', '001006', 'INACTIVO',  (SELECT id_rol FROM rol WHERE nombre = 'CLIENTE'), NULL),
('Sofía López',       'sofia.lopez@gmail.com',            '$2a$10$jvNcGfx1I.Y6FmT2fP6NnuIX4OsnnBJKk7wkO17VlnbZulhTPvAqC', '001007', 'ACTIVO',    (SELECT id_rol FROM rol WHERE nombre = 'CLIENTE'), NULL),
('Martín Torres',     'martin.torres@hotmail.com',        '$2a$10$jvNcGfx1I.Y6FmT2fP6NnuIX4OsnnBJKk7wkO17VlnbZulhTPvAqC', '001008', 'PENDIENTE', (SELECT id_rol FROM rol WHERE nombre = 'CLIENTE'), NULL),
('Valentina Ruiz',    'valentina.ruiz@gmail.com',         '$2a$10$jvNcGfx1I.Y6FmT2fP6NnuIX4OsnnBJKk7wkO17VlnbZulhTPvAqC', '001009', 'ACTIVO',    (SELECT id_rol FROM rol WHERE nombre = 'CLIENTE'), NULL),
('Admin Principal',   'admin@cooperativa.com',            '$2a$10$jvNcGfx1I.Y6FmT2fP6NnuIX4OsnnBJKk7wkO17VlnbZulhTPvAqC', '000001', 'ACTIVO',    (SELECT id_rol FROM rol WHERE nombre = 'ADMIN'),   NULL);


-- ============================================================
-- SEED: token_recuperacion (10 registros)
-- ============================================================
INSERT INTO token_recuperacion (token, fecha_creacion, fecha_expiracion, usado, id_usuario)
VALUES
('tok-aaa111bbb222', '2025-05-01 08:00:00', '2025-05-01 09:00:00', TRUE,  (SELECT id_usuario FROM usuario WHERE email = 'maria.gonzalez@gmail.com')),
('tok-bbb222ccc333', '2025-05-03 10:30:00', '2025-05-03 11:30:00', TRUE,  (SELECT id_usuario FROM usuario WHERE email = 'carlos.rodriguez@hotmail.com')),
('tok-ccc333ddd444', '2025-05-10 14:00:00', '2025-05-10 15:00:00', TRUE,  (SELECT id_usuario FROM usuario WHERE email = 'ana.martinez@yahoo.com')),
('tok-ddd444eee555', '2025-05-15 09:00:00', '2025-05-15 10:00:00', FALSE, (SELECT id_usuario FROM usuario WHERE email = 'luis.fernandez@gmail.com')),
('tok-eee555fff666', '2025-05-18 16:00:00', '2025-05-18 17:00:00', TRUE,  (SELECT id_usuario FROM usuario WHERE email = 'laura.perez@outlook.com')),
('tok-fff666ggg777', '2025-05-20 11:00:00', '2025-05-20 12:00:00', FALSE, (SELECT id_usuario FROM usuario WHERE email = 'diego.sanchez@gmail.com')),
('tok-ggg777hhh888', '2025-05-22 13:00:00', '2025-05-22 14:00:00', TRUE,  (SELECT id_usuario FROM usuario WHERE email = 'sofia.lopez@gmail.com')),
('tok-hhh888iii999', '2025-05-25 08:30:00', '2025-05-25 09:30:00', FALSE, (SELECT id_usuario FROM usuario WHERE email = 'martin.torres@hotmail.com')),
('tok-iii999jjj000', '2025-05-28 17:00:00', '2025-05-28 18:00:00', TRUE,  (SELECT id_usuario FROM usuario WHERE email = 'valentina.ruiz@gmail.com')),
('tok-jjj000kkk111', NOW(),                  NOW() + INTERVAL '1 hour',   FALSE, (SELECT id_usuario FROM usuario WHERE email = 'admin@cooperativa.com'));


-- ============================================================
-- SEED: novedad (10 registros)
-- ============================================================
INSERT INTO novedad (titulo, descripcion, fecha, imagen, tipo)
VALUES
(
    'Apertura de nueva sucursal',
    'Nos complace anunciar la apertura de nuestra nueva sucursal en el centro de la ciudad, disponible para todos los socios a partir del 1 de julio.',
    '2025-06-15', 'noticias/nueva-sucursal.jpg', 'NOTICIA'
),
(
    'Asamblea General Ordinaria 2025',
    'Convocamos a todos los socios a participar de la Asamblea General Ordinaria que se realizará el 20 de junio en el salón principal.',
    '2025-06-20', 'eventos/asamblea-2025.jpg', 'EVENTO'
),
(
    'Nuevas tasas de interés vigentes',
    'A partir del 1 de julio entran en vigencia las nuevas tasas para créditos personales e hipotecarios. Consultá las condiciones en sucursal.',
    '2025-07-01', NULL, 'NOTICIA'
),
(
    'Jornada de educación financiera',
    'Te invitamos a nuestra jornada gratuita de educación financiera orientada a jóvenes socios. Cupos limitados, inscribite antes del 5 de julio.',
    '2025-07-10', 'eventos/jornada-financiera.jpg', 'EVENTO'
),
(
    'Actualización del sistema de home banking',
    'El día 12 de julio realizaremos tareas de mantenimiento en la plataforma de home banking entre las 2 y las 6 de la madrugada.',
    '2025-07-12', NULL, 'NOTICIA'
),
(
    'Feria de emprendedores cooperativos',
    'Participá de la feria anual donde socios emprendedores exhiben sus productos y servicios. Entrada libre y gratuita para toda la comunidad.',
    '2025-07-18', 'eventos/feria-emprendedores.jpg', 'EVENTO'
),
(
    'Incorporación de pagos con QR',
    'A partir de agosto podrás realizar pagos en comercios adheridos escaneando el código QR desde la app de la cooperativa.',
    '2025-07-25', 'noticias/pagos-qr.jpg', 'NOTICIA'
),
(
    'Charla: Planificación financiera familiar',
    'Nuestros asesores brindarán una charla abierta sobre cómo organizar las finanzas del hogar. Sábado 2 de agosto a las 10 hs.',
    '2025-08-02', 'eventos/charla-finanzas.jpg', 'EVENTO'
),
(
    'Nuevo seguro de hogar disponible',
    'Incorporamos a nuestro catálogo el seguro de hogar cooperativo con cobertura integral y cuotas accesibles para todos los socios.',
    '2025-08-10', 'noticias/seguro-hogar.jpg', 'NOTICIA'
),
(
    'Torneo de integración socios 2025',
    'Organizamos un torneo deportivo de integración para socios y sus familias. Actividades para todas las edades. Inscribite en sucursal.',
    '2025-08-20', 'eventos/torneo-integracion.jpg', 'EVENTO'
);


-- ============================================================
-- SEED: producto (10 registros)
-- ============================================================
INSERT INTO producto (titulo, subtitulo, descripcion, imagen)
VALUES
(
    'Caja de Ahorro',
    'Tu dinero disponible siempre',
    'Cuenta de ahorro sin costo de mantenimiento, con tarjeta de débito incluida y acceso a home banking las 24 horas del día.',
    'productos/caja-ahorro.jpg'
),
(
    'Crédito Personal',
    'Financiá tus proyectos',
    'Préstamos personales con tasas preferenciales para socios activos. Hasta 60 cuotas fijas y acreditación en 48 horas hábiles.',
    'productos/credito-personal.jpg'
),
(
    'Crédito Hipotecario',
    'Tu casa propia, más cerca',
    'Línea de crédito hipotecario para construcción, compra o refacción de vivienda. Condiciones especiales para socios con antigüedad.',
    'productos/credito-hipotecario.jpg'
),
(
    'Plazo Fijo',
    'Hacé rendir tus ahorros',
    'Invertí tu dinero con tasas competitivas. Plazos desde 30 días con renovación automática opcional y liquidación de intereses al vencimiento.',
    'productos/plazo-fijo.jpg'
),
(
    'Cuenta Corriente',
    'Para quienes mueven su negocio',
    'Cuenta corriente cooperativa con chequera disponible, ideal para comerciantes y pequeños empresarios socios de la institución.',
    'productos/cuenta-corriente.jpg'
),
(
    'Crédito Pyme',
    'Impulso para tu empresa',
    'Financiamiento especial para pequeñas y medianas empresas asociadas. Montos elevados, plazos flexibles y tasas bonificadas.',
    'productos/credito-pyme.jpg'
),
(
    'Tarjeta de Débito',
    'Pagá en cualquier lugar',
    'Tarjeta de débito asociada a tu caja de ahorro. Aceptada en todos los comercios adheridos a la red y en cajeros automáticos de todo el país.',
    'productos/tarjeta-debito.jpg'
),
(
    'Seguro de Vida',
    'Protección para vos y tu familia',
    'Cobertura de vida accesible para socios activos, sin examen médico previo. Suma asegurada configurable según necesidad.',
    'productos/seguro-vida.jpg'
),
(
    'Crédito Automotor',
    'Manejá tu propio vehículo',
    'Financiación para la compra de vehículos 0 km o usados. Hasta 48 cuotas con tasa fija y sin gastos de otorgamiento para socios.',
    'productos/credito-automotor.jpg'
),
(
    'Fondo de Inversión Cooperativo',
    'Invertí de forma colectiva',
    'Participá de nuestro fondo de inversión solidario. Rentabilidad competitiva con impacto social directo en proyectos de la comunidad.',
    'productos/fondo-inversion.jpg'
);


-- ============================================================
-- SEED: servicio (10 registros)
-- ============================================================
INSERT INTO servicio (titulo, descripcion)
VALUES
(
    'Home Banking',
    'Accedé a tu cuenta desde cualquier dispositivo. Consultá saldos, realizá transferencias y pagá servicios sin moverte de tu casa.'
),
(
    'Débito Automático',
    'Adherí tus facturas y pagalas automáticamente desde tu cuenta. Sin vencimientos olvidados, sin filas, sin preocupaciones.'
),
(
    'Atención Personalizada',
    'Contamos con asesores financieros que te acompañan en cada etapa. Disponible de forma presencial o por videollamada según tu preferencia.'
),
(
    'Cajeros Automáticos',
    'Red de cajeros propios y adheridos en toda la región. Extracciones sin costo en cajeros de la red cooperativa las 24 horas.'
),
(
    'Transferencias Inmediatas',
    'Enviá dinero a cualquier cuenta bancaria del país de forma instantánea a través de nuestra plataforma digital o en sucursal.'
),
(
    'Pago de Servicios',
    'Aboná luz, gas, agua, telefonía e impuestos desde home banking, la app o en nuestras cajas. Rápido, seguro y sin comisiones adicionales.'
),
(
    'Asesoramiento en Inversiones',
    'Nuestros especialistas te orientan para que tus ahorros crezcan. Analizamos tu perfil y te ofrecemos las mejores opciones disponibles.'
),
(
    'Gestión de Seguros',
    'Administrá todos tus seguros desde un solo lugar. Contratación, renovación y denuncias de siniestros con acompañamiento personalizado.'
),
(
    'App Móvil Cooperativa',
    'Descargá nuestra aplicación y llevá tu cuenta en el bolsillo. Disponible para Android e iOS con autenticación biométrica y notificaciones en tiempo real.'
),
(
    'Soporte Técnico Digital',
    'Asistencia para el uso de nuestros canales digitales. Atención por chat, teléfono o turno presencial de lunes a sábados en horario extendido.'
);


-- ============================================================
-- SEED: documento (10 registros)
-- ============================================================
INSERT INTO documento (nombre, descripcion, archivo)
VALUES
(
    'Estatuto Social',
    'Documento fundacional que regula el funcionamiento, organización y gobierno de la cooperativa.',
    'documentos/estatuto-social.pdf'
),
(
    'Reglamento de Créditos',
    'Normativa interna que establece las condiciones, requisitos y procedimientos para el otorgamiento de créditos a socios.',
    'documentos/reglamento-creditos.pdf'
),
(
    'Memoria y Balance 2024',
    'Informe anual con los estados contables y la memoria del ejercicio correspondiente al año 2024.',
    'documentos/memoria-balance-2024.pdf'
),
(
    'Formulario de Adhesión',
    'Formulario oficial para nuevos socios que deseen incorporarse a la cooperativa. Completarlo y presentarlo en sucursal.',
    'documentos/formulario-adhesion.pdf'
),
(
    'Reglamento de Asambleas',
    'Documento que regula la convocatoria, desarrollo y toma de decisiones en las asambleas ordinarias y extraordinarias.',
    'documentos/reglamento-asambleas.pdf'
),
(
    'Política de Privacidad y Datos',
    'Declaración sobre el tratamiento, almacenamiento y protección de los datos personales de los socios conforme a la normativa vigente.',
    'documentos/politica-privacidad.pdf'
),
(
    'Tarifario de Servicios 2025',
    'Listado actualizado de comisiones, aranceles y costos asociados a los productos y servicios ofrecidos por la cooperativa.',
    'documentos/tarifario-2025.pdf'
),
(
    'Manual de Educación Financiera',
    'Guía práctica elaborada por la cooperativa para orientar a los socios en el manejo responsable de sus finanzas personales.',
    'documentos/manual-educacion-financiera.pdf'
),
(
    'Informe de Auditoría Externa 2024',
    'Resultado de la auditoría contable externa realizada sobre los estados financieros del ejercicio 2024.',
    'documentos/auditoria-externa-2024.pdf'
),
(
    'Reglamento Interno de Personal',
    'Normativa que regula las relaciones laborales, derechos y obligaciones del personal que trabaja en la cooperativa.',
    'documentos/reglamento-personal.pdf'
);