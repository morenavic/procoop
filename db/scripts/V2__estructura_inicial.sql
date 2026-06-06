-- ============================================================
-- V1 - Estructura inicial de la base de datos Procoop
-- Crea todas las tablas alineadas con el modelo entidad-relación
-- ============================================================

-- Tabla: rol
CREATE TABLE rol (
    id_rol     SERIAL PRIMARY KEY,
    nombre     VARCHAR(50) NOT NULL UNIQUE
);

-- Tabla: usuario
CREATE TABLE usuario (
    id_usuario     SERIAL PRIMARY KEY,
    nombre         VARCHAR(100) NOT NULL,
    email          VARCHAR(150) NOT NULL UNIQUE,
    contrasenia    VARCHAR(255) NOT NULL,
    numero_cuenta  VARCHAR(50)  NOT NULL UNIQUE,
    estado         VARCHAR(20)  NOT NULL DEFAULT 'PENDIENTE'
                        CHECK (estado IN ('ACTIVO', 'INACTIVO', 'PENDIENTE')),
    id_rol         INTEGER NOT NULL REFERENCES rol(id_rol),
    imagen VARCHAR(255)
);

-- Tabla: token_recuperacion
CREATE TABLE token_recuperacion (
    id_token        SERIAL PRIMARY KEY,
    token           VARCHAR(255) NOT NULL UNIQUE,
    fecha_creacion  TIMESTAMP    NOT NULL DEFAULT NOW(),
    fecha_expiracion TIMESTAMP   NOT NULL,
    usado           BOOLEAN      NOT NULL DEFAULT FALSE,
    id_usuario      INTEGER      NOT NULL REFERENCES usuario(id_usuario)
);

-- Tabla: novedad
-- tipo: NOTICIA | EVENTO
CREATE TABLE novedad (
    id_novedad  SERIAL PRIMARY KEY,
    titulo      VARCHAR(200)  NOT NULL,
    descripcion TEXT          NOT NULL,
    fecha       DATE          NOT NULL,
    imagen      VARCHAR(255),
    tipo        VARCHAR(10)   NOT NULL CHECK (tipo IN ('NOTICIA', 'EVENTO'))
);

-- Tabla: producto (sin slug)
CREATE TABLE producto (
    id_producto SERIAL PRIMARY KEY,
    titulo      VARCHAR(200)  NOT NULL,
    subtitulo   VARCHAR(300),
    descripcion TEXT          NOT NULL,
    imagen      VARCHAR(255)
);

-- Tabla: servicio
CREATE TABLE servicio (
    id_servicio SERIAL PRIMARY KEY,
    titulo      VARCHAR(200) NOT NULL,
    descripcion TEXT         NOT NULL
);

-- Tabla: documento
CREATE TABLE documento (
    id_documento SERIAL PRIMARY KEY,
    nombre       VARCHAR(200) NOT NULL,
    descripcion  TEXT,
    archivo      VARCHAR(255) NOT NULL
);

-- ============================================================
-- Datos iniciales
-- ============================================================

INSERT INTO rol (nombre) VALUES ('ADMINISTRADOR'), ('CLIENTE');

-- Usuario administrador por defecto
-- Contraseña: Admin123! (hasheada con BCrypt — se reemplaza en producción)
INSERT INTO usuario (nombre, email, contrasenia, numero_cuenta, estado, id_rol)
VALUES (
    'Administrador',
    'admin@procoop.com.ar',
    '$2a$10$jvNcGfx1I.Y6FmT2fP6NnuIX4OsnnBJKk7wkO17VlnbZulhTPvAqC',
    '0000',
    'ACTIVO',
    (SELECT id_rol FROM rol WHERE nombre = 'ADMINISTRADOR')
);