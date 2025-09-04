# Topics Application
## Описание
Topics Application — это Spring Boot приложение для управления темами, вопросами и реакциями пользователей. Приложение интегрировано с Keycloak для аутентификации и авторизации, а также использует внешний API для получения данных о погоде.

## Технологии
* Java 17

* Spring Boot 3

* Spring Security с Keycloak

* Spring Data JPA

* PostgreSQL

* MapStruct

* OpenFeign

* Docker & Docker Compose

* Prometheus (мониторинг)

## Требования
* Docker и Docker Compose

* Maven 3.6+

* Java 17

## Установка и запуск
Клонируйте репозиторий:

~~~ bash
git clone <repository-url>
cd topics-app
~~~
Соберите приложение:

~~~ bash
mvn clean package
~~~
## Запустите приложения с помощью Docker Compose:

~~~ bash
docker-compose up --build
~~~
Приложение будет доступно по адресу: http://localhost:8090
Keycloak будет доступен по адресу: http://localhost:8080
Prometheus будет доступен по адресу: http://localhost:9090

## Конфигурация
### Базы данных
Приложение использует две PostgreSQL базы данных:

* topics_db для данных приложения

* keycloak_db для данных Keycloak

### Keycloak
Настройки Keycloak находятся в файле topics-realm.json и автоматически импортируются при запуске.

Данные для входа в админ-панель Keycloak:

* URL: http://localhost:8080

* Логин: admin

* Пароль: admin

### API погоды
Приложение использует OpenWeatherMap API для получения данных о погоде. Настройки API находятся в файле application.properties.

## API Endpoints
### Топики
* GET /topics - получить список топиков

* GET /topics/{id} - получить топик по ID

* POST /topics - создать новый топик

* PUT /topics/{id} - обновить топик

* DELETE /topics/{id} - удалить топик

* GET /topics/weather?city={city} - получить погоду для указанного города

### Вопросы
* GET /questions - получить список вопросов

* GET /questions/{id} - получить вопрос по ID

* POST /questions - создать новый вопрос

* PUT /questions/{id} - обновить вопрос

* DELETE /questions/{id} - удалить вопрос

### Реакции
* GET /reactions - получить список реакций

* POST /reactions - создать новую реакцию

* PUT /reactions/{id} - обновить реакцию

* DELETE /reactions/{id} - удалить реакцию

### Пользователи
* GET /user - получить список пользователей

* GET /user/{id} - получить пользователя по ID

* POST /user - создать нового пользователя

* DELETE /user/{id} - удалить пользователя

## Безопасность
Приложение использует Keycloak для аутентификации и авторизации. Для доступа к защищенным endpoint'ам требуется действительный JWT токен от Keycloak.

Роли пользователей:

* USER - базовая роль пользователя

* ADMIN - роль администратора с расширенными правами

## Мониторинг
Приложение настроено для работы с Prometheus. Метрики доступны по endpoint'у /actuator/prometheus.

## Тестирование
Приложение покрыто комплексными тестами, включающими:

### Типы тестов
* Unit тесты - тестирование мапперов (MapStruct)

* Интеграционные тесты - тестирование репозиториев и сервисов с использованием Testcontainers

* Mock тесты - тестирование контроллеров и сервисов с мокированием зависимостей

* End-to-end тесты - тестирование API endpoints с TestRestTemplate

Запуск тестов
~~~ bash
# Все тесты
mvn test

# Только unit тесты
mvn test -Dtest="*MapperTests"

# Только интеграционные тесты
mvn test -Dtest="*RepositoryTests"

# Только тесты контроллеров
mvn test -Dtest="*ControllerTests"
~~~
### Тестовые конфигурации
Тесты используют отдельный профиль test и Testcontainers для поднятия изолированной базы данных PostgreSQL. Конфигурация тестов находится в src/test/resources/application-test.properties.

### Ключевые тестовые классы
* TopicsMapperTests - тестирование маппинга между Topics и TopicsDTO

* WeatherBeanTests - интеграционные тесты для погодного API

* WeatherMockTests - mock тесты для погодного функционала

* RepositoryTests - тестирование репозиториев с Testcontainers

* DemoApplicationTests - комплексные e2e тесты API endpoints

### Покрытие тестами
Тесты покрывают:

* Все слои приложения (контроллеры, сервисы, репозитории, мапперы)

* Валидацию данных

* Обработку ошибок

* Интеграцию с внешними API

* Безопасность и авторизацию

* Работу с базой данных

Структура проекта
~~~ text
src/
├── main/
│   ├── java/com/example/demo/
│   │   ├── client/          # Feign клиенты для внешних API
│   │   ├── config/          # Конфигурационные классы
│   │   ├── controller/      # REST контроллеры
│   │   ├── dto/            # Data Transfer Objects
│   │   ├── filter/         # Фильтры для поиска
│   │   ├── mapstruct/      # Мапперы для преобразования DTO<->Entity
│   │   ├── model/          # JPA сущности
│   │   ├── repository/     # JPA репозитории
│   │   └── service/        # Сервисный слой
│   └── resources/
│       └── application.properties
Dockerfile
docker-compose.yml
prometheus.yml
~~~
## Разработка
### Для разработки и отладки:

1. Запустите базы данных и Keycloak:

~~~ bash
docker-compose up topics_db keycloak_db keycloak
~~~
2. Запустите приложение локально:

~~~ bash
mvn spring-boot:run
~~~
