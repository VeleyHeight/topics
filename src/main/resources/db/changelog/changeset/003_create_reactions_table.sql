--liquibase formatted sql
--changeset postgres:003_create_reactions_table
create table if not exists reactions(
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY ,
    type VARCHAR(50),
    questions_id INT,
    user_id UUID,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_reactions_questions FOREIGN KEY (questions_id) REFERENCES questions(id) on delete cascade
);