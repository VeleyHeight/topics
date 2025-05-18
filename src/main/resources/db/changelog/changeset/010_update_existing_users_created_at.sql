--liquibase formatted sql
--changeset postgres:010_update_existing_users_created_at

UPDATE users SET created_at = CURRENT_TIMESTAMP
WHERE created_at IS NULL;