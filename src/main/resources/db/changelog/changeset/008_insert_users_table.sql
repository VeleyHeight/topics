--liquibase formatted sql
--changeset postgres:008_insert_users_table

INSERT INTO users (username, password, roles)
VALUES ('user', '$2a$10$Gz9xxo2Zf7q8F6.7xA0EEeC5rzwzehqg5kYII7V1N9/PSe7ZLkIf2', 'USER');
INSERT INTO users (username, password, roles)
VALUES ('admin', '$2a$10$UrTG6c667LQypw0BTg1MMupyBzUcbX7PdTTS.hxvXtwWA2WVAr1JW', 'ADMIN');