--liquibase formatted sql
--changeset postgres:005_insert_questions_table

-- Вопросы для топика "Books and libraries" (topic_id = 2)
insert into questions(question, answer, topic_id) VALUES ('Какая самая старая библиотека в мире?', 'Библиотека Ашшурбанипала, основанная в VII веке до н.э.', 2);
insert into questions(question, answer, topic_id) VALUES ('Как чтение влияет на мозг человека?', 'Чтение стимулирует работу мозга, улучшает память и концентрацию внимания.', 2);

-- Вопросы для топика "Description of a person and a place" (topic_id = 4)
insert into questions(question, answer, topic_id) VALUES ('Как описать внешность человека на английском языке?', 'Для описания внешности можно использовать такие слова, как: tall, short, slim, plump, blonde, brunette, blue-eyed, brown-eyed и т.д.', 4);
insert into questions(question, answer, topic_id) VALUES ('Какие достопримечательности есть в Лондоне?', 'В Лондоне много достопримечательностей, например: Биг-Бен, Тауэрский мост, Букингемский дворец.', 4);

-- Вопросы для топика "Физика" (topic_id = 7)
insert into questions(question, answer, topic_id) VALUES ('Что такое гравитация?', 'Гравитация - это сила притяжения между объектами, имеющими массу.', 7);

-- Вопросы для топика "Химия" (topic_id = 8)
insert into questions(question, answer, topic_id) VALUES ('Что такое pH?', 'pH - это мера кислотности или щелочности раствора.', 8);
insert into questions(question, answer, topic_id) VALUES ('Из чего состоит вода?', 'Вода состоит из двух атомов водорода и одного атома кислорода (H2O).', 8);

-- Вопросы для топика "Биология" (topic_id = 9)
insert into questions(question, answer, topic_id) VALUES ('Что такое фотосинтез?', 'Фотосинтез - это процесс, при котором растения используют солнечный свет для преобразования углекислого газа и воды в глюкозу и кислород.', 9);

-- Вопросы для топика "Математика" (topic_id = 10)
insert into questions(question, answer, topic_id) VALUES ('Что такое число Пи?', 'Число Пи - это математическая константа, равная отношению длины окружности к ее диаметру.', 10);

-- Вопросы для топика "Футбол" (topic_id = 12)
insert into questions(question, answer, topic_id) VALUES ('Сколько игроков в футбольной команде?', 'В футбольной команде 11 игроков.', 12);

-- Вопросы для топика "Баскетбол" (topic_id = 13)
insert into questions(question, answer, topic_id) VALUES ('Какова высота баскетбольного кольца?', 'Высота баскетбольного кольца - 3.05 метра.', 13);

-- Вопросы для топика "Теннис" (topic_id = 14)
insert into questions(question, answer, topic_id) VALUES ('Кто выйграл больше всего турниров Большого шлема?', 'Среди мужчин - Новак Джокович, среди женщин - Маргарет Корт', 14);

-- Вопросы для топика "Живопись" (topic_id = 16)
insert into questions(question, answer, topic_id) VALUES ('Кто написал картину "Мона Лиза"?', 'Картину "Мона Лиза" написал Леонардо да Винчи.', 16);

-- Вопросы для топика "Музыка" (topic_id = 17)
insert into questions(question, answer, topic_id) VALUES ('Кто такой Моцарт?', 'Моцарт - австрийский композитор-классик.', 17);

-- Вопросы для топика "Кино" (topic_id = 18)
insert into questions(question, answer, topic_id) VALUES ('Какой фильм получил больше всего Оскаров?', 'Титаник, Бен-Гур и Властелин колец: Возвращение короля', 18);

-- Вопросы для топика "Театр" (topic_id = 19)
insert into questions(question, answer, topic_id) VALUES ('Кто написал пьесу "Гамлет"?', 'Пьесу "Гамлет" написал Уильям Шекспир.', 19);

-- Вопросы для топика "Программирование" (topic_id = 21)
insert into questions(question, answer, topic_id) VALUES ('Что такое Python?', 'Python - это высокоуровневый язык программирования общего назначения.', 21);

-- Вопросы для топика "Искусственный интеллект" (topic_id = 22)
insert into questions(question, answer, topic_id) VALUES ('Что такое машинное обучение?', 'Машинное обучение - это раздел искусственного интеллекта, изучающий алгоритмы, способные обучаться на данных.', 22);

-- Вопросы для топика "Робототехника" (topic_id = 23)
insert into questions(question, answer, topic_id) VALUES ('Какие бывают типы роботов?', 'Существуют промышленные, сервисные, медицинские, военные и другие типы роботов.', 23);

-- Вопросы для топика "Европа" (topic_id = 25)
insert into questions(question, answer, topic_id) VALUES ('Какие страны входят в состав Европейского союза?', 'В состав Европейского союза входят 27 стран.', 25);
insert into questions(question, answer, topic_id) VALUES ('Какой город является столицей Франции?', 'Столицей Франции является Париж.', 25);