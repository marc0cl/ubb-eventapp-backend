# Database Schema

Below are the MySQL table definitions.

## campus
```sql
CREATE TABLE campus (
    id_campus INT UNSIGNED NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(60) NOT NULL UNIQUE,
    PRIMARY KEY (id_campus)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

## role
```sql
CREATE TABLE role (
    id_rol INT UNSIGNED NOT NULL AUTO_INCREMENT,
    nombre ENUM('ALUMNO','DOCENTE','EXTERNO','MODERADOR','ADMINISTRADOR') UNIQUE,
    PRIMARY KEY (id_rol)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

## tag
```sql
CREATE TABLE tag (
    id_tag INT UNSIGNED NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(60) UNIQUE,
    PRIMARY KEY (id_tag)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

## trofeo
```sql
CREATE TABLE trofeo (
    id_trofeo INT UNSIGNED NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(80) UNIQUE,
    PRIMARY KEY (id_trofeo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

## usuario
```sql
CREATE TABLE usuario (
    id_usuario BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    correo_ubb VARCHAR(120) NOT NULL UNIQUE,
    nombres VARCHAR(80) NOT NULL,
    apellidos VARCHAR(80) NOT NULL,
    username VARCHAR(80) UNIQUE,
    id_campus INT UNSIGNED,
    password VARCHAR(255),
    estado ENUM('ACTIVO','INACTIVO') NOT NULL,
    created_at DATETIME,
    PRIMARY KEY (id_usuario),
    FOREIGN KEY (id_campus) REFERENCES campus(id_campus)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

## grupo
```sql
CREATE TABLE grupo (
    id_grupo BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(120) UNIQUE,
    descripcion TEXT,
    imagen_url VARCHAR(250),
    id_campus INT UNSIGNED,
    PRIMARY KEY (id_grupo),
    FOREIGN KEY (id_campus) REFERENCES campus(id_campus)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

## grupo_miembro
```sql
CREATE TABLE grupo_miembro (
    id_grupo BIGINT UNSIGNED NOT NULL,
    id_usuario BIGINT UNSIGNED NOT NULL,
    rol_grupo ENUM('USUARIO','REPRESENTANTE') NOT NULL,
    PRIMARY KEY (id_grupo, id_usuario),
    FOREIGN KEY (id_grupo) REFERENCES grupo(id_grupo),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

## grupo_tag
```sql
CREATE TABLE grupo_tag (
    id_grupo BIGINT UNSIGNED NOT NULL,
    id_tag INT UNSIGNED NOT NULL,
    PRIMARY KEY (id_grupo, id_tag),
    FOREIGN KEY (id_grupo) REFERENCES grupo(id_grupo),
    FOREIGN KEY (id_tag) REFERENCES tag(id_tag)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

## usuario_rol
```sql
CREATE TABLE usuario_rol (
    id_usuario BIGINT UNSIGNED NOT NULL,
    id_rol INT UNSIGNED NOT NULL,
    PRIMARY KEY (id_usuario, id_rol),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario),
    FOREIGN KEY (id_rol) REFERENCES role(id_rol)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

## evento
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
    PRIMARY KEY (id_evento),
    FOREIGN KEY (id_grupo) REFERENCES grupo(id_grupo),
    FOREIGN KEY (id_creador) REFERENCES usuario(id_usuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

## evento_revision
```sql
CREATE TABLE evento_revision (
    id_evento BIGINT UNSIGNED NOT NULL,
    id_mod BIGINT UNSIGNED,
    estado ENUM('APROBADO','RECHAZADO') NOT NULL,
    comentario VARCHAR(250),
    rev_ts DATETIME,
    PRIMARY KEY (id_evento),
    FOREIGN KEY (id_evento) REFERENCES evento(id_evento),
    FOREIGN KEY (id_mod) REFERENCES usuario(id_usuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

## inscripcion
```sql
CREATE TABLE inscripcion (
    id_evento BIGINT UNSIGNED NOT NULL,
    id_usuario BIGINT UNSIGNED NOT NULL,
    estado ENUM('INSCRITO','CANCELADO','ASISTIO') NOT NULL,
    PRIMARY KEY (id_evento, id_usuario),
    FOREIGN KEY (id_evento) REFERENCES evento(id_evento),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

## amistad
```sql
CREATE TABLE amistad (
    id_usr1 BIGINT UNSIGNED NOT NULL,
    id_usr2 BIGINT UNSIGNED NOT NULL,
    estado ENUM('PENDIENTE','ACEPTADA','BLOQUEADA') NOT NULL,
    PRIMARY KEY (id_usr1, id_usr2),
    FOREIGN KEY (id_usr1) REFERENCES usuario(id_usuario),
    FOREIGN KEY (id_usr2) REFERENCES usuario(id_usuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

## ticket_soporte
```sql
CREATE TABLE ticket_soporte (
    id_ticket BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    id_reporter BIGINT UNSIGNED,
    descripcion TEXT,
    estado ENUM('ABIERTO','CERRADO') NOT NULL,
    PRIMARY KEY (id_ticket),
    FOREIGN KEY (id_reporter) REFERENCES usuario(id_usuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

## usuario_trofeo
```sql
CREATE TABLE usuario_trofeo (
    id_usuario BIGINT UNSIGNED NOT NULL,
    id_trofeo INT UNSIGNED NOT NULL,
    obt_en DATETIME,
    PRIMARY KEY (id_usuario, id_trofeo),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario),
    FOREIGN KEY (id_trofeo) REFERENCES trofeo(id_trofeo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

## token
```sql
CREATE TABLE token (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    access_token VARCHAR(255) UNIQUE,
    token_type ENUM('BEARER'),
    revoked BOOLEAN,
    expired BOOLEAN,
    user_id BIGINT UNSIGNED,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES usuario(id_usuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```
