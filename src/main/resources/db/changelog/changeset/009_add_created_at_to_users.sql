--liquibase formatted sql
--changeset postgres:009_add_created_at_to_users

ALTER TABLE users
    ADD COLUMN created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;