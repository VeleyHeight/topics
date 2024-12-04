--liquibase formatted sql
--changeset postgres:001_create_topics_table

CREATE TABLE topics (
                        id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                        title VARCHAR(255),
                        description VARCHAR(255),
                        parent_id INTEGER,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        CONSTRAINT fk_parent_topics FOREIGN KEY (parent_id) REFERENCES topics(id)
);
