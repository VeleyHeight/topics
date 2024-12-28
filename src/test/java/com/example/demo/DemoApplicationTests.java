package com.example.demo;

import com.example.demo.dto.QuestionsDTO;
import com.example.demo.dto.ReactionsDTO;
import com.example.demo.dto.TopicsDTO;
import com.example.demo.dto.extended.ExtendedTopicsDTO;
import com.example.demo.repository.TopicsRepository;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.hamcrest.text.MatchesPattern;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.restdocs.RestDocsRestAssuredConfigurationCustomizer;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.org.apache.commons.lang3.ObjectUtils;

import java.awt.print.Pageable;
import java.util.HashMap;
import java.util.List;

@Testcontainers
@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest");
    @LocalServerPort
    Integer port;
    @Autowired
    TestRestTemplate restTemplate;

    @Nested
    @DisplayName("Тестирование контроллера топика")
    public class TopicsController {
        @Nested
        @DisplayName("Тестирование методов по возврату страницы со всеми топиками")
        public class getAllTopicsPage {
            @Test
            @DisplayName("Получение страницы для топиков")
            void getPageTopics() {
                ResponseEntity<?> response = restTemplate.getForEntity("/topics", String.class);
                Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
                System.out.println(response);
            }

            @Test
            @DisplayName("Получение страницы для топиков по названию")
            void getPageTopicsTitle() {
                ResponseEntity<?> response = restTemplate.getForEntity("/topics?title=Литература", String.class);
                Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
                System.out.println(response);
            }

            @Test
            @DisplayName("Получение страницы для топиков по не существующему названию")
            void getPageTopicsTitleNull() {
                ResponseEntity<?> response = restTemplate.getForEntity("/topics?title=павввввввввввввввввввввввввввв", String.class);
                Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
                System.out.println(response);
            }
        }

        @Nested
        @DisplayName("Тестирование получения топиков по id")
        public class getTopicsById {
            @Test
            @DisplayName("Получение топика по существующему id")
            void getTopicById() {
                ResponseEntity<?> response = restTemplate.getForEntity("/topics/1", String.class);
                Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
                System.out.println(response.getBody());
            }

            @Test
            @DisplayName("Получение топика по не существующему id")
            void getTopicByWrongId() {
                ResponseEntity<?> response = restTemplate.getForEntity("/topics/199", String.class);
                Assertions.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
                Assertions.assertEquals("Topic with this id is not exist", response.getBody());
                System.out.println(response.getBody());
            }
        }

        @Nested
        @DisplayName("Тестирование расширенного получения топиков по id ")
        public class getTopicsByIdExtended {
            @Test
            @DisplayName("Получение топика по существующему id")
            void getTopicByIdExtended() {
                ResponseEntity<?> response = restTemplate.getForEntity("/topics/extended/2", String.class);
                Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
                System.out.println(response.getBody());
            }

            @Test
            @DisplayName("Расширенное получение топика по не существующему id")
            void getTopicByWrongIdExtended() {
                ResponseEntity<?> response = restTemplate.getForEntity("/topics/extended/199", String.class);
                Assertions.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
                Assertions.assertEquals("Topic with this id does not exist", response.getBody());
                System.out.println(response.getBody());
            }
        }

        @Nested
        @DisplayName("Тестирование создания топика")
        public class createTopics {
            @Test
            @DisplayName("Создание топика")
            void createTopic() {
                TopicsDTO topicsDTO = new TopicsDTO();
                topicsDTO.setTitle("Литература");
                topicsDTO.setDescription("Топики на темы, связанные с книгами и чтением");
                topicsDTO.setParentId(9);
                ResponseEntity<?> response = restTemplate.postForEntity("/topics", topicsDTO, String.class);
                Assertions.assertEquals(response.getStatusCode(), HttpStatus.CREATED);
                System.out.println(response.getBody());
            }
        }

        @Nested
        @DisplayName("Тестирование обновление топика")
        public class updateTopics {
            @Test
            @DisplayName("Обновление топика с корректными данными")
            void updateTopic() {
                TopicsDTO topicsDTO = new TopicsDTO();
                topicsDTO.setTitle("Литература updated");
                topicsDTO.setDescription("Топики на темы, связанные с книгами и чтением updated");
                topicsDTO.setParentId(null);
                ResponseEntity<?> response = restTemplate.exchange("/topics/1", HttpMethod.PUT, new HttpEntity<>(topicsDTO), String.class);
                Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
                System.out.println(response.getBody());
            }

            @Test
            @DisplayName("Поиск топика по несуществующего Id")
            void updateTopicNullId() {
                TopicsDTO topicsDTO = new TopicsDTO();
                topicsDTO.setTitle("Литература updated");
                topicsDTO.setDescription("Топики на темы, связанные с книгами и чтением updated");
                topicsDTO.setParentId(null);
                ResponseEntity<?> response = restTemplate.exchange("/topics/1222", HttpMethod.PUT, new HttpEntity<>(topicsDTO), String.class);
                Assertions.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
                Assertions.assertEquals("Topic with this id is not exist", response.getBody());
                System.out.println(response.getBody());
            }

            @Test
            @DisplayName("Проверка ссылки parentId на само себя")
            void updateTopicRecursionId() {
                TopicsDTO topicsDTO = new TopicsDTO();
                topicsDTO.setTitle("Литература updated");
                topicsDTO.setDescription("Топики на темы, связанные с книгами и чтением updated");
                topicsDTO.setParentId(1);
                ResponseEntity<?> response = restTemplate.exchange("/topics/1", HttpMethod.PUT, new HttpEntity<>(topicsDTO), String.class);
                Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
                Assertions.assertEquals("Parent id cannot be equals topic id", response.getBody());
                System.out.println(response.getBody());
            }
        }

        @Nested
        @DisplayName("Тестирование выборочное обновление топика")
        public class patchTopics {
            @Test
            @DisplayName("Обновление названия топика с корректными данными")
            void patchTopicTitle() {
                HashMap<String, String> requestBody = new HashMap<>();
                requestBody.put("title", "Литература updated");
                ResponseEntity<?> response = restTemplate.exchange("/topics/1", HttpMethod.PATCH, new HttpEntity<>(requestBody), String.class);
                Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
                System.out.println(response.getBody());
            }

            @Test
            @DisplayName("Пустое тело запроса")
            void patchTopicNullBody() {
                HashMap<String, String> requestBody = new HashMap<>();
                ResponseEntity<?> response = restTemplate.exchange("/topics/1", HttpMethod.PATCH, new HttpEntity<>(requestBody), String.class);
                Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
                Assertions.assertEquals("Input is empty", response.getBody());
                System.out.println(response.getBody());
            }

            @Test
            @DisplayName("Проверка ссылки parentId на само себя")
            void patchTopicRecursionId() {
                TopicsDTO topicsDTO = new TopicsDTO();
                topicsDTO.setTitle("Литература updated");
                topicsDTO.setDescription("Топики на темы, связанные с книгами и чтением updated");
                topicsDTO.setParentId(1);
                ResponseEntity<?> response = restTemplate.exchange("/topics/1", HttpMethod.PATCH, new HttpEntity<>(topicsDTO), String.class);
                Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
                Assertions.assertEquals("Parent id cannot be equals topic id", response.getBody());
                System.out.println(response.getBody());
            }
        }

        @Nested
        @DisplayName("Тестирование удаление топика")
        public class deleteTopics {
            @Test
            @DisplayName("Удаление существующего топика")
            void deleteTopic() {
                ResponseEntity<?> response = restTemplate.exchange("/topics/7", HttpMethod.DELETE, new HttpEntity<>(String.class), String.class);
                Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
                System.out.println(response.getBody());
            }

            @Test
            @DisplayName("Ошибка удаления топика с несуществующим id")
            void deleteTopicId() {
                ResponseEntity<?> response = restTemplate.exchange("/topics/999", HttpMethod.DELETE, new HttpEntity<>(String.class), String.class);
                Assertions.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
                Assertions.assertEquals("Topic with this id does not exist", response.getBody());
                System.out.println(response.getBody());
            }
        }
    }

    @Nested
    @DisplayName("Тестирование контроллера вопросов")
    public class QuestionsController {
        @Nested
        @DisplayName("Тестирование методов по возврату страницы со всеми вопросами")
        public class getAllQuestionsPage {
            @Test
            @DisplayName("Получение страницы для топиков")
            void getPageQuestions() {
                ResponseEntity<?> response = restTemplate.getForEntity("/questions", String.class);
                Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
                System.out.println(response);
            }

            @Test
            @DisplayName("Получение страницы для вопросов по тексту вопроса")
            void getPageQuestionsByText() {
                ResponseEntity<?> response = restTemplate.getForEntity("/questions?questions=какой", String.class);
                Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
                System.out.println(response);
            }

            @Test
            @DisplayName("Получение страницы для вопросов по не существующему тексту вопроса")
            void getPageQuestionsByTextNull() {
                ResponseEntity<?> response = restTemplate.getForEntity("/questions?questions=абвгдеёжзийклмнопрстуфхцчшщъыьэюя", String.class);
                Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
                System.out.println(response);
            }
        }

        @Nested
        @DisplayName("Тестирование получения вопроса по id")
        public class getQuestionsById {
            @Test
            @DisplayName("Получение вопроса по существующему id")
            void getQuestionById() {
                ResponseEntity<?> response = restTemplate.getForEntity("/questions/3", String.class);
                Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
                System.out.println(response.getBody());
            }

            @Test
            @DisplayName("Получение вопроса по не существующему id")
            void getQuestionByWrongId() {
                ResponseEntity<?> response = restTemplate.getForEntity("/questions/999", String.class);
                Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
                Assertions.assertEquals("Question with this id is not exist", response.getBody());
                System.out.println(response.getBody());
            }
        }

        @Nested
        @DisplayName("Тестирование расширенного получения топиков по id вопроса")
        public class getTopicsByIdExtended {
            @Test
            @DisplayName("Получение топика по существующему id вопроса")
            void getTopicByIdExtended() {
                ResponseEntity<?> response = restTemplate.getForEntity("/questions/extended/6", String.class);
                Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
                System.out.println(response.getBody());
            }

            @Test
            @DisplayName("Расширенное получение топика по не существующему id вопроса")
            void getTopicByWrongIdExtended() {
                ResponseEntity<?> response = restTemplate.getForEntity("/questions/extended/199", String.class);
                Assertions.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
                Assertions.assertEquals("Question with this id does not exist", response.getBody());
                System.out.println(response.getBody());
            }
        }

        @Nested
        @DisplayName("Тестирование создания вопроса")
        public class createQuestions {
            @Test
            @DisplayName("Создание вопроса")
            void createQuestion() {
                QuestionsDTO questionsDTO = new QuestionsDTO();
                questionsDTO.setQuestion("Какой самый высокий горный пик в мире?");
                questionsDTO.setAnswer("Эверест");
                questionsDTO.setTopicId(1);
                questionsDTO.set_popular(false);
                ResponseEntity<?> response = restTemplate.postForEntity("/questions", questionsDTO, String.class);
                Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
                System.out.println(response.getBody());
            }
        }

        @Nested
        @DisplayName("Тестирование обновления вопроса")
        public class updateQuestions {
            @Test
            @DisplayName("Обновление вопроса с корректными данными")
            void updateQuestion() {
                QuestionsDTO questionsDTO = new QuestionsDTO();
                questionsDTO.setQuestion("Какой самый высокий горный пик в мире? (обновлено)");
                questionsDTO.setAnswer("Эверест (обновлено)");
                questionsDTO.setTopicId(2);
                questionsDTO.set_popular(true);
                ResponseEntity<?> response = restTemplate.exchange("/questions/4", HttpMethod.PUT, new HttpEntity<>(questionsDTO), String.class);
                Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
                System.out.println(response.getBody());
            }

            @Test
            @DisplayName("Поиск вопроса по несуществующему Id")
            void updateQuestionNullId() {
                QuestionsDTO questionsDTO = new QuestionsDTO();
                questionsDTO.setQuestion("Какой самый высокий горный пик в мире? (обновлено)");
                questionsDTO.setAnswer("Эверест (обновлено)");
                questionsDTO.setTopicId(2);
                questionsDTO.set_popular(true);
                ResponseEntity<?> response = restTemplate.exchange("/questions/12345", HttpMethod.PUT, new HttpEntity<>(questionsDTO), String.class);
                Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
                Assertions.assertEquals("Question with this id is not exist", response.getBody());
                System.out.println(response.getBody());
            }
        }

        @Nested
        @DisplayName("Тестирование выборочное обновление вопроса")
        public class patchQuestions {
            @Test
            @DisplayName("Обновление текста вопроса с корректными данными")
            void patchQuestionText() {
                HashMap<String, String> requestBody = new HashMap<>();
                requestBody.put("questions", "Какой самый высокий вулкан в мире?");
                ResponseEntity<?> response = restTemplate.exchange("/questions/5", HttpMethod.PATCH, new HttpEntity<>(requestBody), String.class);
                Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
                System.out.println(response.getBody());
            }

            @Test
            @DisplayName("Пустое тело запроса")
            void patchQuestionNullBody() {
                HashMap<String, String> requestBody = new HashMap<>();
                ResponseEntity<?> response = restTemplate.exchange("/questions/7", HttpMethod.PATCH, new HttpEntity<>(requestBody), String.class);
                Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
                Assertions.assertEquals("Input is empty", response.getBody());
                System.out.println(response.getBody());
            }

            @Test
            @DisplayName("Попытка обновления несуществующего вопроса")
            void patchQuestionWrongId() {
                HashMap<String, String> requestBody = new HashMap<>();
                requestBody.put("answer", "Анды");
                ResponseEntity<?> response = restTemplate.exchange("/questions/54321", HttpMethod.PATCH, new HttpEntity<>(requestBody), String.class);
                Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
                Assertions.assertEquals("Question with this id is not exist", response.getBody());
                System.out.println(response.getBody());
            }
        }

        @Nested
        @DisplayName("Тестирование удаления вопроса")
        public class deleteQuestions {
            @Test
            @DisplayName("Удаление существующего вопроса")
            void deleteQuestion() {
                ResponseEntity<?> response = restTemplate.exchange("/questions/2", HttpMethod.DELETE, new HttpEntity<>(String.class), String.class);
                Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
                System.out.println(response.getBody());
            }

            @Test
            @DisplayName("Ошибка удаления вопроса с несуществующим id")
            void deleteQuestionId() {
                ResponseEntity<?> response = restTemplate.exchange("/questions/888", HttpMethod.DELETE, new HttpEntity<>(String.class), String.class);
                Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
                Assertions.assertEquals("Question with this id does not exist", response.getBody());
                System.out.println(response.getBody());
            }
        }
    }

    @Nested
    @DisplayName("Тестирование контроллера реакций")
    public class ReactionsController {
        @Nested
        @DisplayName("Тестирование методов по возврату всех реакций")
        public class getAllReactions {
            @Test
            @DisplayName("Получение списка всех реакций")
            void getAllReaction() {
                ResponseEntity<?> response = restTemplate.getForEntity("/reactions", List.class);
                Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
                System.out.println(response.getBody());
            }
        }

        @Nested
        @DisplayName("Тестирование создания реакции")
        public class createReactions {
            @Test
            @DisplayName("Создание реакции с корректными данными")
            void createReaction() {
                ReactionsDTO reactionsDTO = new ReactionsDTO();
                reactionsDTO.setUser_id("a239a2cd-6e43-4fc6-b72a-073a6f3c0230");
                reactionsDTO.setType("like");
                reactionsDTO.setQuestionsId(8);
                ResponseEntity<?> response = restTemplate.postForEntity("/reactions", reactionsDTO, ReactionsDTO.class);
                Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
                Assertions.assertNotNull(response.getBody());
                System.out.println(response.getBody());
            }
        }

        @Nested
        @DisplayName("Тестирование обновления реакции")
        public class updateReactions {
            @Test
            @DisplayName("Обновление реакции с корректными данными")
            void updateReaction() {
                ReactionsDTO reactionsDTO = new ReactionsDTO();
                reactionsDTO.setUser_id("a239a2cd-6e43-4fc6-b72a-073a6f3c0230");
                reactionsDTO.setType("dislike");
                reactionsDTO.setQuestionsId(2);
                ResponseEntity<?> response = restTemplate.exchange("/reactions/6", HttpMethod.PUT, new HttpEntity<>(reactionsDTO), ReactionsDTO.class);
                Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
                Assertions.assertNotNull(response.getBody());
                System.out.println(response.getBody());
            }

            @Test
            @DisplayName("Обновление реакции по несуществующему Id")
            void updateReactionNullId() {
                ReactionsDTO reactionsDTO = new ReactionsDTO();
                reactionsDTO.setUser_id("a239a2cd-6e43-4fc6-b72a-073a6f3c0230");
                reactionsDTO.setType("dislike");
                reactionsDTO.setQuestionsId(2);
                ResponseEntity<?> response = restTemplate.exchange("/reactions/1333", HttpMethod.PUT, new HttpEntity<>(reactionsDTO), String.class);
                Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
                Assertions.assertEquals("Reactions with this id is not exist", response.getBody());
                System.out.println(response.getBody());
            }
        }

        @Nested
        @DisplayName("Тестирование частичного обновления реакции")
        public class patchReaction {
            @Test
            @DisplayName("Частичное обновление типа реакции")
            void patchReactionType() {
                HashMap<String, String> requestBody = new HashMap<>();
                requestBody.put("type", "dislike");
                ResponseEntity<?> response = restTemplate.exchange("/reactions/4", HttpMethod.PATCH, new HttpEntity<>(requestBody), ReactionsDTO.class);
                Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
                Assertions.assertNotNull(response.getBody());
                System.out.println(response.getBody());
            }

            @Test
            @DisplayName("Пустое тело запроса реакции")
            void patchReactionNullBody() {
                HashMap<String, String> requestBody = new HashMap<>();
                ResponseEntity<?> response = restTemplate.exchange("/reactions/5", HttpMethod.PATCH, new HttpEntity<>(requestBody), String.class);
                Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
                Assertions.assertEquals("Input is empty", response.getBody());
                System.out.println(response.getBody());
            }

            @Test
            @DisplayName("Попытка частичного обновления несуществующей реакции")
            void patchNonExistingReaction() {
                HashMap<String, String> requestBody = new HashMap<>();
                requestBody.put("type", "like");
                ResponseEntity<?> response = restTemplate.exchange("/reactions/999", HttpMethod.PATCH, new HttpEntity<>(requestBody), String.class);
                Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
                Assertions.assertEquals("Reactions with this id is not exist", response.getBody());
                System.out.println(response.getBody());
            }
        }

        @Nested
        @DisplayName("Тестирование удаления реакции")
        public class deleteReactions {
            @Test
            @DisplayName("Удаление существующей реакции")
            void deleteReaction() {
                ResponseEntity<?> response = restTemplate.exchange("/reactions/1", HttpMethod.DELETE, new HttpEntity<>(String.class), String.class);
                Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
                Assertions.assertNotNull(response.getBody());
                System.out.println(response.getBody());
            }

            @Test
            @DisplayName("Попытка удаления несуществующей реакции")
            void deleteNonExistingReaction() {
                ResponseEntity<?> response = restTemplate.exchange("/reactions/9999", HttpMethod.DELETE, new HttpEntity<>(String.class), String.class);
                Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
                Assertions.assertEquals("Reactions with this id is not exist", response.getBody());
                System.out.println(response.getBody());
            }
        }
    }

    @Nested
    @DisplayName("Валидация топиков")
    public class ValidationTopics {
        @Test
        @DisplayName("Кастомная валидация parentId на существующие id")
        void createTopicCustomValidationParentId() {
            TopicsDTO topicsDTO = new TopicsDTO();
            topicsDTO.setTitle("Литература");
            topicsDTO.setDescription("Топики на темы, связанные с книгами и чтением");
            topicsDTO.setParentId(999999);
            ResponseEntity<?> response = restTemplate.postForEntity("/topics", topicsDTO, String.class);
            Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
            Assertions.assertEquals("{\"parentId\":\"Invalid parent id\"}", response.getBody());
            System.out.println(response.getBody());
        }

        @Test
        @DisplayName("Валидация blank Title")
        void createTopicValidationNullTitle() {
            TopicsDTO topicsDTO = new TopicsDTO();
            topicsDTO.setTitle("        ");
            topicsDTO.setDescription("Топики на темы, связанные с книгами и чтением");
            topicsDTO.setParentId(null);
            ResponseEntity<?> response = restTemplate.postForEntity("/topics", topicsDTO, String.class);
            Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
            Assertions.assertEquals("{\"title\":\"Title is blank\"}", response.getBody());
            System.out.println(response.getBody());
        }

        @Test
        @DisplayName("Валидация длины Title")
        void createTopicValidationTitle() {
            TopicsDTO topicsDTO = new TopicsDTO();
            topicsDTO.setTitle("Лит");
            topicsDTO.setDescription("Топики на темы, связанные с книгами и чтением");
            topicsDTO.setParentId(null);
            ResponseEntity<?> response = restTemplate.postForEntity("/topics", topicsDTO, String.class);
            Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
            Assertions.assertEquals("{\"title\":\"Title size must be between 5 and 200\"}", response.getBody());
            System.out.println(response.getBody());
        }

        @Test
        @DisplayName("Валидация null description")
        void updateTopicValidationNullDescription() {
            TopicsDTO topicsDTO = new TopicsDTO();
            topicsDTO.setTitle("Литература updated");
            topicsDTO.setDescription("     ");
            topicsDTO.setParentId(null);
            ResponseEntity<?> response = restTemplate.exchange("/topics/5", HttpMethod.PUT, new HttpEntity<>(topicsDTO), String.class);
            Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
            Assertions.assertEquals("{\"description\":\"Description is blank\"}", response.getBody());
            System.out.println(response.getBody());
        }

        @Test
        @DisplayName("Валидация size description")
        void updateTopicValidationDescription() {
            TopicsDTO topicsDTO = new TopicsDTO();
            topicsDTO.setTitle("Литература updated");
            topicsDTO.setDescription("Топ");
            topicsDTO.setParentId(null);
            ResponseEntity<?> response = restTemplate.exchange("/topics/1", HttpMethod.PUT, new HttpEntity<>(topicsDTO), String.class);
            Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
            Assertions.assertEquals("{\"description\":\"Description size must be between 5 and 200\"}", response.getBody());
            System.out.println(response.getBody());
        }
    }

    @Nested
    @DisplayName("Валидация вопросов")
    public class ValidationQuestions {
        @Test
        @DisplayName("Кастомная валидация topicId на существующие id")
        void createQuestionsValidationTopicId() {
            QuestionsDTO questionsDTO = new QuestionsDTO();
            questionsDTO.setQuestion("Какой самый высокий горный пик в мире?");
            questionsDTO.setAnswer("Эверест");
            questionsDTO.setTopicId(0);
            questionsDTO.set_popular(false);
            ResponseEntity<?> response = restTemplate.postForEntity("/questions", questionsDTO, String.class);
            Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            Assertions.assertEquals("{\"topicId\":\"Invalid topic id\"}", response.getBody());
            System.out.println(response.getBody());
        }

        @Test
        @DisplayName("Валидация questions на длину от 5 до 1000")
        void createQuestionsValidationQuiestions() {
            QuestionsDTO questionsDTO = new QuestionsDTO();
            questionsDTO.setQuestion("Как");
            questionsDTO.setAnswer("Эверест");
            questionsDTO.setTopicId(1);
            questionsDTO.set_popular(false);
            ResponseEntity<?> response = restTemplate.postForEntity("/questions", questionsDTO, String.class);
            Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            Assertions.assertEquals("{\"question\":\"Question size must be between 5 and 1000\"}", response.getBody());
            System.out.println(response.getBody());
        }

        @Test
        @DisplayName("Валидация questions на пустой вопрос")
        void createQuestionsValidationBlankQuiestions() {
            QuestionsDTO questionsDTO = new QuestionsDTO();
            questionsDTO.setAnswer("Эверест");
            questionsDTO.setQuestion("                   ");
            questionsDTO.setTopicId(1);
            questionsDTO.set_popular(false);
            ResponseEntity<?> response = restTemplate.postForEntity("/questions", questionsDTO, String.class);
            Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            Assertions.assertEquals("{\"question\":\"Question is blank\"}", response.getBody());
            System.out.println(response.getBody());
        }

        @Test
        @DisplayName("Валидация answer на размер ответа")
        void createQuestionsValidationSizeAnswer() {
            QuestionsDTO questionsDTO = new QuestionsDTO();
            questionsDTO.setAnswer("Эве");
            questionsDTO.setQuestion("Какой самый высокий горный пик в мире?");
            questionsDTO.setTopicId(1);
            questionsDTO.set_popular(false);
            ResponseEntity<?> response = restTemplate.postForEntity("/questions", questionsDTO, String.class);
            Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            Assertions.assertEquals("{\"answer\":\"Answer size must be between 5 and 10000\"}", response.getBody());
            System.out.println(response.getBody());
        }
    }

    @Nested
    @DisplayName("Валидация реакции")
    public class ValidationReactions {
        @Test
        @DisplayName("Кастомная валидация questionsId на существующие id")
        void createReactionValidationQuestionsId() {
            ReactionsDTO reactionsDTO = new ReactionsDTO();
            reactionsDTO.setUser_id("a239a2cd-6e43-4fc6-b72a-073a6f3c0230");
            reactionsDTO.setType("like");
            reactionsDTO.setQuestionsId(0);
            ResponseEntity<?> response = restTemplate.postForEntity("/reactions", reactionsDTO, String.class);
            Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            Assertions.assertEquals("{\"questionsId\":\"Invalid question id\"}", response.getBody());
            System.out.println(response.getBody());
        }

        @Test
        @DisplayName("Валидация userId на пустой запрос")
        void createReactionValidationBlankId() {
            ReactionsDTO reactionsDTO = new ReactionsDTO();
            reactionsDTO.setUser_id("               ");
            reactionsDTO.setType("like");
            reactionsDTO.setQuestionsId(1);
            ResponseEntity<?> response = restTemplate.postForEntity("/reactions", reactionsDTO, String.class);
            Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            Assertions.assertEquals("{\"user_id\":\"User is blank\"}", response.getBody());
            System.out.println(response.getBody());
        }

        @Test
        @DisplayName("Валидация type на размер реакции")
        void createReactionValidation() {
            ReactionsDTO reactionsDTO = new ReactionsDTO();
            reactionsDTO.setUser_id("a239a2cd-6e43-4fc6-b72a-073a6f3c0230");
            reactionsDTO.setType("п");
            reactionsDTO.setQuestionsId(1);
            ResponseEntity<?> response = restTemplate.postForEntity("/reactions", reactionsDTO, String.class);
            Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            Assertions.assertEquals("{\"type\":\"Type size must be between 2 and 50\"}", response.getBody());
            System.out.println(response.getBody());
        }
    }
}
