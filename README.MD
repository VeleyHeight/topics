# Spring Boot CRUD API

## Описание

Это Spring Boot приложение реализует RESTful CRUD API для управления сущностями: `Topics`, `Reactions` и `Questions`. Приложение также интегрируется с внешним API для получения погоды по названию города.

## Технологии

*   **Язык программирования:** Java 17
*   **Фреймворк:** Spring Boot (3.x)
*   **Управление зависимостями:** Maven
*   **Ускорение разработки:** Lombok
*   **Веб-разработка:** Spring Web
*   **Работа с данными:** Spring Data JPA
*   **Миграции базы данных:** Liquibase
*   **Драйвер базы данных:** PostgreSQL
*   **Валидация данных:** Validation API (jakarta.validation)
*   **Мониторинг и управление:** Spring Boot Actuator
*   **Метрики:** Prometheus
*   **Интеграционное тестирование:** Testcontainers, JUnit 5
*   **HTTP клиент:** OpenFeign

## Предварительные требования

Убедитесь, что на вашем компьютере установлены следующие программы:

*   Java 17
*   Maven
*   Docker (для запуска через Docker Compose)

## Сборка проекта

Выполните следующую команду в корневой директории проекта:

```bash
mvn clean install
```
Для сборки проекта с пропуском тестов используйте команду:

```bash
mvn clean install -DskipTests
```
# Запуск приложения
## Запуск через Spring Boot Run
Вы можете запустить приложение непосредственно из вашей IDE или выполнив следующую команду в корневой директории проекта:
```bash
mvn spring-boot:run
```
## Запуск через Docker Compose
1. Убедитесь, что Docker установлен на вашем компьютере.
2. Соберите Docker Compose образы приложения:
```
docker compose build
```
3. Запустите приложение и PostgreSQL через Docker Compose:
```
docker-compose up -d
```
Убедитесь, что в вашем docker-compose.yml файле настроено подключение к базе данных PostgreSQL.
# API Endpoints
## Топики
* **GET /topics/weather?city={city}**: Получение погоды в указанном городе (использует OpenFeign для вызова внешнего API).
* **GET /topics**: Получение всех топиков с пагинацией и фильтрацией по названию.
  * Параметры: page, size, title (необязательный).
* **GET /topics/{id}**: Получение топика по ID.
* **POST /topics**: Создание нового топика.
* **PUT /topics/{id}**: Обновление существующего топика.
* **PATCH /topics/{id}**: Частичное обновление существующего топика.
* **DELETE /topics/{id}**: Удаление топика.
* **GET /topics/extended/{id}**: Получение топика по ID с расширенной информацией.
##  Questions
*  **GET /questions**: Получение всех вопросов с пагинацией и фильтрацией по тексту вопроса.
   * Параметры: ```page```, ```size```, ```questions``` (необязательный).
*  **GET /questions/{id}**: Получение вопроса по ID.
*  **POST /questions**: Создание нового вопроса.
*  **PUT /questions/{id}**: Обновление существующего вопроса.
*  **PATCH /questions/{id}**: Частичное обновление существующего вопроса.
*  **DELETE /questions/{id}**: Удаление вопроса.
*  **GET /questions/extended/{id}**: Получение вопроса по ID с расширенной информацией.
##   Reactions
*   **GET /reactions**: Получение всех реакций.
*   **POST /reactions**: Создание новой реакции.
*   **PUT /reactions/{id}**: Обновление существующей реакции.
*   **PATCH /reactions/{id}**: Частичное обновление существующей реакции.
*   **DELETE /reactions/{id}**: Удаление реакции.
#    Тестирование
Для запуска интеграционных тестов, использующих Testcontainers, выполните следующую команду:
```
mvn test
```
Интеграционные тесты разворачивают контейнер PostgreSQL с использованием Testcontainers и проверяют взаимодействие с базой данных.
# Миграции базы данных
Для управления схемой базы данных используется Liquibase. Файлы миграций находятся в директории ```src/main/resources/db/changelog.``` Миграции применяются автоматически при запуске приложения.
# Конфигурация
Конфигурация приложения находится в файле ```src/main/resources/application.yml```. В текущей версии проекта разделение конфигурации на профили (например, application-local.yml) не реализовано.
# Мониторинг
Для мониторинга приложения используется Spring Boot Actuator. Доступ к эндпоинтам Actuator можно получить по адресу ```http://localhost:8080/actuator``` (порт может отличаться в зависимости от вашей конфигурации).

Интеграция с Graphit в текущей версии проекта не реализована.

Для просмотра метрик Prometheus перейдите по адресу ```http://localhost:8080/actuator/prometheus```.


# Docker
## Dockerfile
В корне проекта находится Dockerfile, который используется для сборки Docker образа приложения.

## Docker Compose
Файл docker-compose.yml в корне проекта используется для запуска приложения вместе с зависимостями (например, PostgreSQL и Prometheus). Пример docker-compose.yml:
```
volumes:
  pg_topics:
  prometheus_data:

services:
  topics_db:
    image: postgres
    container_name: topics_db
    restart: always
    environment:
      - POSTGRES_USER=postgres
      - POSTGRES_PASSWORD=1234
      - POSTGRES_DB=topics
    volumes:
      - pg_topics:/var/lib/postgresql/data
    ports:
      - "5432:5432"
  prometheus:
    image: prom/prometheus
    container_name: prometheus
    restart: always
    volumes:
      - prometheus_data:/prometheus
      - ./prometheus.yml:/etc/prometheus/prometheus.yml
    ports:
      - "9090:9090"
  app:
    restart: always
    build: .
    ports:
      - "8080:8080"
    depends_on:
      - topics_db
    environment:
      - SPRING_DATASOURCE_URL=jdbc:postgresql://topics_db:5432/topics
      - SPRING_DATASOURCE_USERNAME=postgres
      - SPRING_DATASOURCE_PASSWORD=1234
```
Замените ```your-app-name```, ```your_db_user```, ```your_db_password``` и ```your_db_name``` на соответствующие значения. Создайте файл ```prometheus.yml``` с базовой конфигурацией для сбора метрик.
# Запуск приложения через Docker Compose
1. Соберите Docker Compose образы (если еще не сделали): ```docker compose build```
2. Запустите приложение через Docker Compose: ```docker compose up -d```


