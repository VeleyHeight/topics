--liquibase formatted sql
--changeset postgres:create-tables

CREATE TABLE topics (
                        id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                        title VARCHAR(255),
                        description VARCHAR(255),
                        parent_id INTEGER,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        CONSTRAINT fk_parent_topics FOREIGN KEY (parent_id) REFERENCES topics(id)
);
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
create table reactions(
                          id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY ,
                          type VARCHAR(50),
                          questions_id INT,
                          user_id UUID,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          CONSTRAINT fk_reactions_questions FOREIGN KEY (questions_id) REFERENCES questions(id)

);