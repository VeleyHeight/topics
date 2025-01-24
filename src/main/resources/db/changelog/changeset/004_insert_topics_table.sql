--liquibase formatted sql
--changeset postgres:004_insert_topics_table
--Инсерты для тестов должны отрабатывать только при запуске спринг-профиля test, добавь application-test.yml и вызывай эти скрипты только в нем!!!!

insert into topics(title, description, parent_id) VALUES ('Литература','Топики на темы, связанные с книгами и чтением',null);
insert into topics(title, description, parent_id) VALUES ('Books and libraries','В этом топике вы можете вы можете прочитать об истории некоторых знаменитых библиотек, а также о влиянии чтения на нашу жизнь',1);
insert into topics(title, description, parent_id) VALUES ('Моя жизнь','Хобби, как проходит мой день, любимые животные',null);
insert into topics(title, description, parent_id) VALUES ('Description of a person and a place','В этом топике учимся рассказывать о человеке и его месте проживания',3);
insert into topics(title, description, parent_id) VALUES ('Дом Кати','Небольшой по объему текст в котором описывается дом в котором живет Катя и ее брат Боб, на английском языке с русским переводом и пояснениями к переводу.',null);
insert into topics(title, description, parent_id) VALUES ('Наука', 'Топики на научные темы', null);
insert into topics(title, description, parent_id) VALUES ('Физика', 'Топики по физике', 6);
insert into topics(title, description, parent_id) VALUES ('Химия', 'Топики по химии', 6);
insert into topics(title, description, parent_id) VALUES ('Биология', 'Топики по биологии', 6);
insert into topics(title, description, parent_id) VALUES ('Математика', 'Топики по математике', 6);
insert into topics(title, description, parent_id) VALUES ('Спорт', 'Топики на спортивные темы', null);
insert into topics(title, description, parent_id) VALUES ('Футбол', 'Топики про футбол', 11);
insert into topics(title, description, parent_id) VALUES ('Баскетбол', 'Топики про баскетбол', 11);
insert into topics(title, description, parent_id) VALUES ('Теннис', 'Топики про теннис', 11);
insert into topics(title, description, parent_id) VALUES ('Искусство', 'Топики на темы искусства', null);
insert into topics(title, description, parent_id) VALUES ('Живопись', 'Топики про живопись', 15);
insert into topics(title, description, parent_id) VALUES ('Музыка', 'Топики про музыку', 15);
insert into topics(title, description, parent_id) VALUES ('Кино', 'Топики про кино', 15);
insert into topics(title, description, parent_id) VALUES ('Театр', 'Топики про театр', 15);
insert into topics(title, description, parent_id) VALUES ('Технологии', 'Топики на темы технологий', null);
insert into topics(title, description, parent_id) VALUES ('Программирование', 'Топики про программирование', 20);
insert into topics(title, description, parent_id) VALUES ('Искусственный интеллект', 'Топики про искусственный интеллект', 20);
insert into topics(title, description, parent_id) VALUES ('Робототехника', 'Топики про робототехнику', 20);
insert into topics(title, description, parent_id) VALUES ('Путешествия', 'Топики на темы путешествий', null);
insert into topics(title, description, parent_id) VALUES ('Европа', 'Топики про путешествия по Европе', 24);