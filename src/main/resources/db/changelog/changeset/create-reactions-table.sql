--liquibase formatted sql
--changeset postgres:create-reactions-table
create table reactions(
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY ,
    type VARCHAR(50),
    questions_id INT,
    user_id UUID,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_reactions_questions FOREIGN KEY (questions_id) REFERENCES questions(id)

);