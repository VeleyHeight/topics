package com.example.demo;

import com.example.demo.dto.TopicsDTO;
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
    @Autowired
    private TopicsRepository topicsRepository;

    @Nested
    @DisplayName("Тестирование контроллера")
    public class TopicsController{
        @Nested
        @DisplayName("Тестирование методов по возврату страницы со всеми топиками")
        public class getAllTopicsPage{
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
        public class getTopicsById{
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
                Assertions.assertEquals("Topic with this id is not exist",response.getBody());
                System.out.println(response.getBody());
            }
        }
        @Nested
        @DisplayName("Тестирование создания топика")
        public class createTopics{
            @Test
            @DisplayName("Создание топика")
            void createTopic() {
                TopicsDTO topicsDTO = new TopicsDTO();
                topicsDTO.setTitle("Литература");
                topicsDTO.setDescription("Топики на темы, связанные с книгами и чтением");
                topicsDTO.setParentId(null);
                ResponseEntity<?> response = restTemplate.postForEntity("/topics",topicsDTO, String.class);
                Assertions.assertEquals(response.getStatusCode(), HttpStatus.CREATED);
                System.out.println(response.getBody());
            }
            @Test
            @DisplayName("Кастомная валидация parentId")
            void createTopicCustomValidationParentId() {
                TopicsDTO topicsDTO = new TopicsDTO();
                topicsDTO.setTitle("Литература");
                topicsDTO.setDescription("Топики на темы, связанные с книгами и чтением");
                topicsDTO.setParentId(999999);
                ResponseEntity<?> response = restTemplate.postForEntity("/topics",topicsDTO, String.class);
                Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
                Assertions.assertEquals("{\"parentId\":\"Invalid parent id\"}",response.getBody());
                System.out.println(response.getBody());
            }
            @Test
            @DisplayName("Валидация Title")
            void createTopicValidationTitle() {
                TopicsDTO topicsDTO = new TopicsDTO();
                topicsDTO.setTitle("Лит");
                topicsDTO.setDescription("Топики на темы, связанные с книгами и чтением");
                topicsDTO.setParentId(null);
                ResponseEntity<?> response = restTemplate.postForEntity("/topics",topicsDTO, String.class);
                Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
                Assertions.assertEquals("{\"title\":\"Title size must be between 5 and 200\"}",response.getBody());
                System.out.println(response.getBody());
            }
        }
        @Nested
        @DisplayName("Тестирование обновление топика")
        public class updateTopics{
            @Test
            @DisplayName("Обновление топика с корректными данными")
            void updateTopic() {
                TopicsDTO topicsDTO = new TopicsDTO();
                topicsDTO.setTitle("Литература updated");
                topicsDTO.setDescription("Топики на темы, связанные с книгами и чтением updated");
                topicsDTO.setParentId(null);
                ResponseEntity<?> response = restTemplate.exchange("/topics/1", HttpMethod.PUT,new HttpEntity<>(topicsDTO), String.class);
                Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
                System.out.println(response.getBody());
            }
            @Test
            @DisplayName("Валидация null description")
            void updateTopicValidationNullDescription() {
                TopicsDTO topicsDTO = new TopicsDTO();
                topicsDTO.setTitle("Литература updated");
                topicsDTO.setDescription(null);
                topicsDTO.setParentId(null);
                ResponseEntity<?> response = restTemplate.exchange("/topics/1", HttpMethod.PUT,new HttpEntity<>(topicsDTO), String.class);
                Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
                Assertions.assertEquals("{\"description\":\"Description is blank\"}",response.getBody());
                System.out.println(response.getBody());
            }
            @Test
            @DisplayName("Валидация description")
            void updateTopicValidationDescription() {
                TopicsDTO topicsDTO = new TopicsDTO();
                topicsDTO.setTitle("Литература updated");
                topicsDTO.setDescription("Топ");
                topicsDTO.setParentId(null);
                ResponseEntity<?> response = restTemplate.exchange("/topics/1", HttpMethod.PUT,new HttpEntity<>(topicsDTO), String.class);
                Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
                Assertions.assertEquals("{\"description\":\"Description size must be between 5 and 200\"}",response.getBody());
                System.out.println(response.getBody());
            }
//            @Test
//            @DisplayName("Валидация Id")
//            void updateTopicNullId() {
//                TopicsDTO topicsDTO = new TopicsDTO();
//                topicsDTO.setTitle("Литература updated");
//                topicsDTO.setDescription("Топ");
//                topicsDTO.setParentId(null);
//                ResponseEntity<?> response = restTemplate.exchange("/topics/1222", HttpMethod.PUT,new HttpEntity<>(topicsDTO), String.class);
//                Assertions.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
//                Assertions.assertEquals("Topic with this id is not exist",response.getBody());
//                System.out.println(response.getBody());
//            }
        }
        @Nested
        @DisplayName("Тестирование выборочное обновление топика")
        public class patchTopics{
            @Test
            @DisplayName("Обновление названия топика с корректными данными")
            void patchTopicTitle() {
                HashMap<String, String> requestBody = new HashMap<>();
                requestBody.put("title", "Литература updated");
                ResponseEntity<?> response = restTemplate.exchange("/topics/1", HttpMethod.PATCH,new HttpEntity<>(requestBody), String.class);
                Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
                System.out.println(response.getBody());
            }
            @Test
            @DisplayName("Валидация пустого обновления")
            void patchTopicTitleValidation() {
                HashMap<String, String> requestBody = new HashMap<>();
                ResponseEntity<?> response = restTemplate.exchange("/topics/1", HttpMethod.PATCH,new HttpEntity<>(requestBody), String.class);
                Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
                Assertions.assertEquals("Input is empty",response.getBody());
                System.out.println(response.getBody());
            }
        }
    }
}
