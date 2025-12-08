USE speakweb_db;
-- usuarios
INSERT INTO users (user_id, name, surname, email, password, role) VALUES
(1, 'Pepe', 'Admin', 'admin1@speakweb.com', '$2a$10$aR4cmP1apdKlroVihs0JMemeHan/86VwXQhw8d89iR7eWiSYgbFOi', 'ADMIN'),
(2, 'Mario', 'Admin', 'admin2@speakweb.com', '$2a$10$6xSqqZbzq4Cp/4x62sr.5u8S6d97kuazQuwVwHF/bAuz1g1yUZLtu', 'ADMIN'),
(3, 'Juan', 'Principiante', 'juan@speakweb.com', '$2a$10$i8RdKM0TNn2IRStYissKT.Nx4Lc4lgPEqwr1WG3Os8SEaxuRjnZYe', 'USER'),
(4, 'Tom', 'England', 'tom@speakweb.com', '$2a$10$VSyLtzxbaUY277CkNcsWFO2Ul5wLbxzLSGX2PfPneIbuG5.REZ.ea', 'USER'),
(5, 'Laura', 'España', 'laura@speakweb.com', '$2a$10$xI2Cf1B9PcxDlImt579GVu73eBhL/lOviY7JorRiFHoAobPlloh.S', 'USER');
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
INSERT INTO b_groups (group_id, name, language1_id, language2_id) VALUES
(1, 'Intercambio Inglés-Español', 1, 2),
(2, 'Tandem Alemán-Español', 4, 2),
(3, 'Conversation Club French-English', 5, 1);
-- miembros de los grupos
INSERT INTO group_members (id, user_id, group_id, is_expert) VALUES
(1, 1, 1, 0), -- Pepe grupo 1
(2, 4, 1, 1), -- Tom
(3, 5, 1, 0); -- Laura
-- eventos
INSERT INTO events (event_id, group_id, title, description, start_time, external_link, location) VALUES
(1, 1, 'Quedada de Inglés', 'Vamos a practicar speaking', '2025-12-20 16:00:00', NULL, 'Irish Pub, Madrid'),
(2, 1, 'Tapas en español', 'Practicamos español tomando tapas', '2025-12-27 19:00:00', NULL, 'Bar La Plaza, Sevilla');
-- participantes en eventos
INSERT INTO event_participants (id, event_id, user_id) VALUES
(1, 1, 4); -- Tom en evento 1