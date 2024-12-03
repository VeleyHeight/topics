--liquibase formatted sql
--changeset postgres:create-questions-table

CREATE TABLE questions (
                           id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                           question TEXT,
                           answer TEXT,
                           is_popular BOOLEAN DEFAULT FALSE,
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           topic_id INTEGER NOT NULL,
                           CONSTRAINT fk_questions_topics FOREIGN KEY (topic_id) REFERENCES topics(id)
);

-- CREATE OR REPLACE FUNCTION update_modified_column()
-- RETURNS TRIGGER AS $$
-- BEGIN
--     NEW.updated_at = CURRENT_TIMESTAMP;
-- RETURN NEW;
-- END;
-- $$ language 'plpgsql';
--
-- CREATE TRIGGER update_questions_modtime
--     BEFORE UPDATE ON questions
--     FOR EACH ROW
--     EXECUTE FUNCTION update_modified_column();