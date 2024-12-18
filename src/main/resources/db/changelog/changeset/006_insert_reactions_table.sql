--liquibase formatted sql
--changeset postgres:006_insert_reactions_table
-- Реакции для вопроса "Какая самая старая библиотека в мире?" (questions_id = 1)
CREATE EXTENSION if not exists pgcrypto;
insert into reactions(type, questions_id, user_id) VALUES ('like', 1, gen_random_uuid());
insert into reactions(type, questions_id, user_id) VALUES ('dislike', 1, gen_random_uuid());

-- Реакции для вопроса "Как чтение влияет на мозг человека?" (questions_id = 2)
insert into reactions(type, questions_id, user_id) VALUES ('like', 2, gen_random_uuid());
insert into reactions(type, questions_id, user_id) VALUES ('like', 2, gen_random_uuid());

-- Реакции для вопроса "Как описать внешность человека на английском языке?" (questions_id = 3)
insert into reactions(type, questions_id, user_id) VALUES ('like', 3, gen_random_uuid());
insert into reactions(type, questions_id, user_id) VALUES ('dislike', 3, gen_random_uuid());

-- Реакции для вопроса "Какие достопримечательности есть в Лондоне?" (questions_id = 4)
insert into reactions(type, questions_id, user_id) VALUES ('like', 4, gen_random_uuid());
insert into reactions(type, questions_id, user_id) VALUES ('like', 4, gen_random_uuid());

-- Реакции для вопроса "Что такое гравитация?" (questions_id = 5)
insert into reactions(type, questions_id, user_id) VALUES ('like', 5, gen_random_uuid());
insert into reactions(type, questions_id, user_id) VALUES ('dislike', 5, gen_random_uuid());

-- Реакции для вопроса "Что такое pH?" (questions_id = 6)
insert into reactions(type, questions_id, user_id) VALUES ('like', 6, gen_random_uuid());
insert into reactions(type, questions_id, user_id) VALUES ('dislike', 6, gen_random_uuid());

-- Реакции для вопроса "Из чего состоит вода?" (questions_id = 7)
insert into reactions(type, questions_id, user_id) VALUES ('like', 7, gen_random_uuid());
insert into reactions(type, questions_id, user_id) VALUES ('like', 7, gen_random_uuid());

-- Реакции для вопроса "Что такое фотосинтез?" (questions_id = 8)
insert into reactions(type, questions_id, user_id) VALUES ('like', 8, gen_random_uuid());
insert into reactions(type, questions_id, user_id) VALUES ('dislike', 8, gen_random_uuid());

-- Реакции для вопроса "Что такое число Пи?" (questions_id = 9)
insert into reactions(type, questions_id, user_id) VALUES ('like', 9, gen_random_uuid());
insert into reactions(type, questions_id, user_id) VALUES ('dislike', 9, gen_random_uuid());

-- Реакции для вопроса "Сколько игроков в футбольной команде?" (questions_id = 10)
insert into reactions(type, questions_id, user_id) VALUES ('like', 10, gen_random_uuid());
insert into reactions(type, questions_id, user_id) VALUES ('like', 10, gen_random_uuid());

-- Реакции для вопроса "Какова высота баскетбольного кольца?" (questions_id = 11)
insert into reactions(type, questions_id, user_id) VALUES ('like', 11, gen_random_uuid());
insert into reactions(type, questions_id, user_id) VALUES ('like', 11, gen_random_uuid());

-- Реакции для вопроса "Кто выйграл больше всего турниров Большого шлема?" (questions_id = 12)
insert into reactions(type, questions_id, user_id) VALUES ('like', 12, gen_random_uuid());
insert into reactions(type, questions_id, user_id) VALUES ('dislike', 12, gen_random_uuid());

-- Реакции для вопроса "Кто написал картину 'Мона Лиза'?" (questions_id = 13)
insert into reactions(type, questions_id, user_id) VALUES ('like', 13, gen_random_uuid());
insert into reactions(type, questions_id, user_id) VALUES ('like', 13, gen_random_uuid());

-- Реакции для вопроса "Кто такой Моцарт?" (questions_id = 14)
insert into reactions(type, questions_id, user_id) VALUES ('like', 14, gen_random_uuid());
insert into reactions(type, questions_id, user_id) VALUES ('dislike', 14, gen_random_uuid());

-- Реакции для вопроса "Какой фильм получил больше всего Оскаров?" (questions_id = 15)
insert into reactions(type, questions_id, user_id) VALUES ('like', 15, gen_random_uuid());
insert into reactions(type, questions_id, user_id) VALUES ('like', 15, gen_random_uuid());

-- Реакции для вопроса "Кто написал пьесу 'Гамлет'?" (questions_id = 16)
insert into reactions(type, questions_id, user_id) VALUES ('like', 16, gen_random_uuid());
insert into reactions(type, questions_id, user_id) VALUES ('like', 16, gen_random_uuid());

-- Реакции для вопроса "Что такое Python?" (questions_id = 17)
insert into reactions(type, questions_id, user_id) VALUES ('like', 17, gen_random_uuid());
insert into reactions(type, questions_id, user_id) VALUES ('dislike', 17, gen_random_uuid());

-- Реакции для вопроса "Что такое машинное обучение?" (questions_id = 18)
insert into reactions(type, questions_id, user_id) VALUES ('like', 18, gen_random_uuid());
insert into reactions(type, questions_id, user_id) VALUES ('dislike', 18, gen_random_uuid());

-- Реакции для вопроса "Какие бывают типы роботов?" (questions_id = 19)
insert into reactions(type, questions_id, user_id) VALUES ('like', 19, gen_random_uuid());
insert into reactions(type, questions_id, user_id) VALUES ('like', 19, gen_random_uuid());

-- Реакции для вопроса "Какие страны входят в состав Европейского союза?" (questions_id = 20)
insert into reactions(type, questions_id, user_id) VALUES ('like', 20, gen_random_uuid());
insert into reactions(type, questions_id, user_id) VALUES ('dislike', 20, gen_random_uuid());

-- Реакции для вопроса "Какой город является столицей Франции?" (questions_id = 21)
insert into reactions(type, questions_id, user_id) VALUES ('like', 21, gen_random_uuid());
insert into reactions(type, questions_id, user_id) VALUES ('like', 21, gen_random_uuid());