# Procoop — Rediseño Web Institucional

Rediseño del sitio web institucional de Procoop. Incluye módulo público, módulo cliente y módulo administrador, con gestión de contenidos, usuarios y documentación.

Repositorio: https://github.com/morenavic/procoop.git

---

## Estructura del repositorio
procoop/
├── frontend/        # Aplicación Angular (módulo web)
└── backend/         # API REST con Spring Boot

---

## Requisitos previos

### General
- Git

### Frontend
- Node.js v24.16.0
- Angular CLI v22.0.0
- Bun v1.2.21

### Backend
- Java 21
- Maven
- Spring Boot 3.x

### Base de datos
- PostgreSQL 18
- Base de datos: `procoop`

---

## Configuración de la base de datos

1. Crear la base de datos en PostgreSQL:

```sql
CREATE DATABASE procoop;
```

2. Configurar las variables de entorno del backend (o editar `application.yml`):
DB_URL=jdbc:postgresql://localhost:5432/procoop
DB_USERNAME=tu_usuario
DB_PASSWORD=tu_contraseña

---

## Instalación y ejecución

### Backend

```bash
cd backend
./mvnw spring-boot:run
```

El servidor quedará disponible en: `http://localhost:8080`

### Frontend

```bash
cd frontend
bun install
ng serve
```

La aplicación quedará disponible en: `http://localhost:4200`

---

## Variables de entorno — Backend

| Variable      | Valor por defecto                          | Descripción              |
|---------------|--------------------------------------------|--------------------------|
| `DB_URL`      | `jdbc:postgresql://localhost:5432/procoop` | URL de la base de datos  |
| `DB_USERNAME` | —                                          | Usuario de PostgreSQL    |
| `DB_PASSWORD` | —                                          | Contraseña de PostgreSQL |
| `PORT`        | `8080`                                     | Puerto del servidor      |

---

## Tecnologías utilizadas

### Frontend
- Angular 22 (Standalone)
- TypeScript
- Reactive Forms

### Backend
- Java 21
- Spring Boot 3
- Spring Data JPA
- Spring Validation

### Base de datos
- PostgreSQL 18

### Control de versiones
- Git / GitHub
