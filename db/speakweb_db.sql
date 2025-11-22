CREATE DATABASE speakweb_db;
USE speakweb_db;

CREATE TABLE users (
	user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(45) NOT NULL,
    surname VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(15) NOT NULL DEFAULT 'USER',
    CHECK (role IN ('USER', 'ADMIN')) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (language_id) REFERENCES languages(language_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
    
CREATE TABLE b_groups ( -- Grupos bilingües - groups: p. reservada
	group_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
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
    FOREIGN KEY (user_id) REFERENCES users(user_id),
	FOREIGN KEY (group_id) REFERENCES b_groups(group_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE events ( -- solo un experto puede abrirlos
	event_id INT AUTO_INCREMENT PRIMARY KEY,
    group_id INT NOT NULL,
    title VARCHAR(150) NOT NULL,
    description VARCHAR(2000),
    start_time DATETIME NOT NULL,
    external_link VARCHAR(255) NOT NULL, -- Enlace a llamada de zoom, google meet, etc
	FOREIGN KEY (group_id) REFERENCES b_groups(group_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Antes PK compuesta (event_id, user_id) s/ modelo E-R
-- Después id autoincremental para simplificar en Spring
CREATE TABLE event_participants (
	id INT AUTO_INCREMENT PRIMARY KEY, -- PK única
    event_id INT NOT NULL,
    user_id INT NOT NULL,
    FOREIGN KEY (event_id) REFERENCES events(event_id),
	FOREIGN KEY (user_id) REFERENCES users(user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
    