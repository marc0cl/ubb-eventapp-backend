# AGENTS.md - Repo Guide

## Overview
This repository contains the Java/Spring backend for the Event App. Documentation for the database schema is under `docs/DATABASE.md` and API reference examples are in `docs/API_REFERENCE.md`. A Postman collection is available under `postman/`.

## Build & Test
Use the provided script to compile and run the tests:

```bash
./build.sh
```

It invokes `mvn -B clean verify` using Java 17 and fails on any test error.

## Running the Application
After a successful build, run the generated jar:

```bash
java -jar target/*.jar &
```
Wait for `http://localhost:8080/actuator/health` to report `"status":"UP"`.

## Example API Usage
Below are selected examples from the [API reference](docs/API_REFERENCE.md).

### Register
```bash
curl -X POST http://localhost:8080/auth/register \
  -H 'Content-Type: application/json' \
  -d '{"email":"user@example.com","password":"secret","username":"user","firstName":"John","lastName":"Doe","isExternal":false}'
```
Response:
```json
{
  "accessToken": "<jwt>",
  "refreshToken": "<jwt>"
}
```

### Update Profile
```bash
curl -X PUT http://localhost:8080/users \
  -H 'Content-Type: application/json' \
  -d '{"id":1,"correoUbb":"user@ubb.cl","nombres":"Juan","apellidos":"Pérez","username":"juan","campus":{"id":1}}'
```
Returns the updated `User` object.

### Create Event
```bash
curl -X POST http://localhost:8080/events \
  -H 'Content-Type: application/json' \
  -d '{"titulo":"Charla","descripcion":"Descripción","fechaInicio":"2024-01-01T10:00:00","fechaFin":"2024-01-01T12:00:00","lugar":"Auditorio","aforoMax":100}'
```
Returns the created `Event`.

## Database Schema Snippet
Important tables include:

```sql
CREATE TABLE campus (
    id_campus INT UNSIGNED NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(60) NOT NULL UNIQUE,
    PRIMARY KEY (id_campus)
);
```

```sql
CREATE TABLE role (
    id_rol INT UNSIGNED NOT NULL AUTO_INCREMENT,
    nombre ENUM('ALUMNO','DOCENTE','EXTERNO','MODERADOR','ADMINISTRADOR') UNIQUE,
    PRIMARY KEY (id_rol)
);
```

```sql
CREATE TABLE evento (
    id_evento BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    origen_tipo ENUM('USUARIO','GRUPO','OTRO'),
    id_grupo BIGINT UNSIGNED,
    id_creador BIGINT UNSIGNED NOT NULL,
    titulo VARCHAR(180) NOT NULL,
    descripcion TEXT,
    fecha_inicio DATETIME NOT NULL,
    fecha_fin DATETIME NOT NULL,
    lugar VARCHAR(120),
    aforo_max INT,
    visibilidad ENUM('PUBLICO','PRIVADO'),
    estado_validacion ENUM('PENDIENTE','APROBADO','RECHAZADO'),
    PRIMARY KEY (id_evento)
);
```

See `docs/DATABASE.md` for full definitions.

## Data Models
The API returns JSON structures that map closely to the JPA entities in `src/main/java/com/ubb/eventapp/model`. Key examples:

### User
```json
{
  "id": 1,
  "correoUbb": "user@ubb.cl",
  "nombres": "Juan",
  "apellidos": "Pérez",
  "username": "juan",
  "campus": {"id": 1},
  "estado": "ACTIVO"
}
```

### Event
```json
{
  "id": 10,
  "titulo": "Evento de prueba",
  "fechaInicio": "2024-01-01T10:00:00",
  "fechaFin": "2024-01-01T12:00:00",
  "lugar": "Auditorio",
  "aforoMax": 100,
  "estadoValidacion": "PENDIENTE"
}
```

For additional endpoints, consult `docs/API_REFERENCE.md`.
