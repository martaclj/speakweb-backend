USE speakweb_db;
-- usuarios
INSERT INTO users (user_id, name, surname, email, password, role, avatar_url, bio) VALUES
(1, 'Pepe', 'Admin', 'admin1@speakweb.com', '$2a$10$aR4cmP1apdKlroVihs0JMemeHan/86VwXQhw8d89iR7eWiSYgbFOi', 'ADMIN', NULL, 'Administrador del sistema'),
(2, 'Mario', 'Admin', 'admin2@speakweb.com', '$2a$10$6xSqqZbzq4Cp/4x62sr.5u8S6d97kuazQuwVwHF/bAuz1g1yUZLtu', 'ADMIN', NULL, 'Administrador'),
(3, 'Juan', 'Principiante', 'juan@speakweb.com', '$2a$10$i8RdKM0TNn2IRStYissKT.Nx4Lc4lgPEqwr1WG3Os8SEaxuRjnZYe', 'USER', 'https://i.pravatar.cc/150?u=juan', '¡Hola! Quiero aprender inglés para viajar.'),
(4, 'Tom', 'England', 'tom@speakweb.com', '$2a$10$VSyLtzxbaUY277CkNcsWFO2Ul5wLbxzLSGX2PfPneIbuG5.REZ.ea', 'USER', 'https://i.pravatar.cc/150?u=tom', 'Native English speaker living in Madrid.'),
(5, 'Laura', 'España', 'laura@speakweb.com', '$2a$10$xI2Cf1B9PcxDlImt579GVu73eBhL/lOviY7JorRiFHoAobPlloh.S', 'USER', 'https://i.pravatar.cc/150?u=laura', 'Me encanta enseñar español y aprender nuevas culturas.');
-- idiomas
INSERT INTO languages (language_id, code, name) VALUES 
(1, 'EN', 'English'),
(2, 'ES', 'Español'),
(3, 'IT', 'Italiano'),
(4, 'DE', 'Deutsch'),
(5, 'FR', 'Français');
-- idiomas de los usuarios
INSERT INTO user_languages (id, user_id, language_id, type, level) VALUES
(1, 3, 1, 'LEARNER', 'B2'), -- Juan aprende inglés
(2, 4, 1, 'NATIVE', 'C2'), -- TOM NATIVO INGLÉS
(3, 4, 2, 'LEARNER', 'A2'), -- TOM aprende español
(4, 5, 1, 'LEARNER', 'B2'), -- LAURA aprende inglés
(5, 5, 2, 'NATIVE', 'C2'); -- LAURA es nativa de español
-- grupos
INSERT INTO b_groups (group_id, name, description, image_url, language1_id, language2_id) VALUES
(1, 'Intercambio Inglés-Español', 'Grupo abierto para practicar conversación casual.', 'https://images.unsplash.com/photo-1523240795612-9a054b0db644?auto=format&fit=crop&w=800&q=80', 1, 2),
(2, 'Tandem Alemán-Español', 'Buscamos gente seria para mejorar gramática y vocabulario técnico', NULL, 4, 2),
(3, 'Conversation Club French-English', NULL, NULL, 5, 1);
-- miembros de los grupos
INSERT INTO group_members (id, user_id, group_id, is_expert) VALUES
(1, 1, 1, 0), -- Pepe grupo 1
(2, 4, 1, 1), -- Tom
(3, 5, 1, 0); -- Laura
-- eventos
INSERT INTO events (event_id, group_id, creator_id, type, title, description, start_time, external_link, location, image_url) VALUES
(1, 1, 4, 'PRESENTIAL', 'Quedada de Inglés', 'Vamos a practicar speaking', '2025-12-20 16:00:00', NULL, 'Irish Pub, Madrid', 'https://images.unsplash.com/photo-1572116469696-31de0f17cc34?auto=format&fit=crop&w=800&q=80'),
(2, 1, 5, 'PRESENTIAL', 'Tapas en español', 'Practicamos español tomando tapas', '2025-12-27 19:00:00', NULL, 'Bar La Plaza, Sevilla', 'https://images.unsplash.com/photo-1515443961218-a51367888e4b?auto=format&fit=crop&w=800&q=80');
-- participantes en eventos
INSERT INTO event_participants (id, event_id, user_id) VALUES
(1, 1, 4); -- Tom en evento 1
-- valoraciones de eventos
INSERT INTO user_ratings (reviewer_id, reviewed_user_id, event_id, score, comments) VALUES
(3, 4, 1, 5, '¡Tom es un anfitrión genial, la charla fue muy fluida!'),
(5, 4, 1, 4, 'Buen evento, aunque el sitio era un poco ruidoso.');
-- denuncias o reportes
INSERT INTO user_reports (reporter_id, reported_user_id, reason) VALUES
(4, 3, 'El usuario no se presentó a la quedada'),
(5, 4, 'Me ha enviado mensajes con spam');