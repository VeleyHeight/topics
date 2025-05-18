--liquibase formatted sql
--changeset postgres:007_create_users_table

CREATE TABLE IF NOT EXISTS users(
    id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    username VARCHAR(50) not null unique,
    password varchar(128) not null,
    roles varchar(20) not null default 'USER' check (roles in ('USER', 'ADMIN'))
);