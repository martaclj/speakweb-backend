CREATE DATABASE IF NOT EXISTS speakweb_db;
USE speakweb_db;

CREATE TABLE IF NOT EXISTS users (
	user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(45) NOT NULL,
    surname VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(15) NOT NULL DEFAULT 'USER',
    avatar_url VARCHAR(255), -- FOTO DE PERFIL
    bio VARCHAR(255), -- PRESENTACIÓN USER OPCIONAL
    CHECK (role IN ('USER', 'ADMIN')) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- TABLA DE LOGS - copia de los borrados automáticamente
CREATE TABLE IF NOT EXISTS deleted_users (
	log_id INT AUTO_INCREMENT PRIMARY KEY,
    original_user_id INT, -- id que tenía en users
    name VARCHAR(45),
    surname VARCHAR(100),
    email VARCHAR(100),
    password VARCHAR(100),
    role VARCHAR(15),
    avatar_url VARCHAR(255),
    bio VARCHAR(255),
    deleted_at DATETIME DEFAULT current_timestamp -- Fecha de baja por admin
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- TRIGGER para copiar datos del usuario borrado a la tabla de LOGS
DROP TRIGGER IF EXISTS before_user_delete;

CREATE TRIGGER before_user_delete
BEFORE DELETE ON users
FOR EACH ROW
INSERT INTO deleted_users (original_user_id, name, surname, email, password, role, avatar_url, bio)
VALUES (OLD.user_id, OLD.name, OLD.surname, OLD.email, OLD.password, OLD.role, OLD.avatar_url, OLD.bio);

CREATE TABLE languages (
	language_id INT AUTO_INCREMENT PRIMARY KEY,
    code CHAR(2) NOT NULL UNIQUE,
    name VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Antes PK compuesta (user_id, language_id) según modelo E-R
-- Después id autoincremental para simplificar en Spring
CREATE TABLE user_languages (
	id INT AUTO_INCREMENT PRIMARY KEY, -- PK única
    user_id INT NOT NULL,
    language_id INT NOT NULL,
    type VARCHAR(7) NOT NULL,
    level VARCHAR(5) NOT NULL,
    CHECK (type IN ('NATIVE', 'LEARNER')),
	CHECK (level IN ('A1', 'A2', 'B1', 'B2', 'C1', 'C2')),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (language_id) REFERENCES languages(language_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
    
CREATE TABLE b_groups ( -- Grupos bilingües - groups: p. reservada
	group_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    image_url VARCHAR(255),
    language1_id INT NOT NULL,
    language2_id INT NOT NULL,
    FOREIGN KEY (language1_id) REFERENCES languages(language_id),
	FOREIGN KEY (language2_id) REFERENCES languages(language_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Antes PK compuesta (user_id, group_id) s/ modelo E-R
-- Después id autoincremental para simplificar en Spring
CREATE TABLE group_members (
	id INT AUTO_INCREMENT PRIMARY KEY, -- PK única
    user_id INT NOT NULL,
    group_id INT NOT NULL,
    is_expert BOOLEAN NOT NULL DEFAULT FALSE, -- condición para abrir eventos
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
	FOREIGN KEY (group_id) REFERENCES b_groups(group_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE events ( -- solo un experto puede abrirlos
	event_id INT AUTO_INCREMENT PRIMARY KEY,
    group_id INT NOT NULL,
    creator_id INT,
    type VARCHAR(20) NOT NULL, -- ONLINE o PRESENTIAL
    title VARCHAR(150) NOT NULL,
    description VARCHAR(2000),
    start_time DATETIME NOT NULL,
    external_link VARCHAR(255), -- Enlace a llamada de zoom, google meet, etc
	location VARCHAR(255), -- Dirección física si el evento es presencial: Cafetería, universidad, academia..
    image_url VARCHAR(255),
    FOREIGN KEY (group_id) REFERENCES b_groups(group_id),
    FOREIGN KEY (creator_id) REFERENCES users(user_id) ON DELETE SET NULL -- no se borra su evento si el usuario es borrado
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Antes PK compuesta (event_id, user_id) s/ modelo E-R
-- Después id autoincremental para simplificar en Spring
CREATE TABLE event_participants (
	id INT AUTO_INCREMENT PRIMARY KEY, -- PK única
    event_id INT NOT NULL,
    user_id INT NOT NULL,
    FOREIGN KEY (event_id) REFERENCES events(event_id),
	FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE -- se borra como participante si es borrado como user
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
    