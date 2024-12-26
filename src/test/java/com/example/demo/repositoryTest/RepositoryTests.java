package com.example.demo.repositoryTest;

import com.example.demo.dto.ReactionsDTO;
import com.example.demo.model.Questions;
import com.example.demo.model.Reactions;
import com.example.demo.model.Topics;
import com.example.demo.repository.QuestionsRepository;
import com.example.demo.repository.ReactionsRepository;
import com.example.demo.repository.TopicsRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RepositoryTests {
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest");
    @Autowired
    private TopicsRepository topicsRepository;

    @Autowired
    private QuestionsRepository questionsRepository;

    @Autowired
    private ReactionsRepository reactionsRepository;

    private Topics topics;
    private Questions questions;
    private Reactions reactions;

    @BeforeEach
    void setUp() {
        topics = new Topics();
        topics.setTitle("Литература 90-х годов");
        topics.setDescription("Топики на темы, связанные с книгами и чтением");
        topics.setParentId(null);
        topicsRepository.save(topics);
        questions = new Questions();
        questions.setQuestion("Кто написал 'Generation П'?");
        questions.setAnswer("Виктор Пелевин");
        questions.setTopicId(topics);
        questionsRepository.save(questions);
        reactions = new Reactions();
        reactions.setType("like");
        reactions.setUser_id(UUID.randomUUID());
        reactions.setQuestionsId(questions);
        reactionsRepository.save(reactions);
    }

    @AfterEach
    void throwDown() {
        topicsRepository.delete(topics);
        questionsRepository.delete(questions);
        reactionsRepository.delete(reactions);
    }

    @Nested
    @DisplayName("Тестирование репозитория топиков")
    class topicsRepositoryTests {
        @Test
        @DisplayName("Поиск топика по id")
        void findById() {
            Topics findTopics = topicsRepository.findById(topics.getId()).orElse(null);
            Assertions.assertNotNull(topics);
            Assertions.assertEquals(topics.getTitle(), findTopics.getTitle());
            Assertions.assertEquals(topics.getDescription(), findTopics.getDescription());
            Assertions.assertEquals(topics.getParentId(), findTopics.getParentId());
        }

        @Test
        @DisplayName("Поиск всех топиков")
        void findAllTopics() {
            List<Topics> allTopics = topicsRepository.findAll();
            Assertions.assertTrue(!allTopics.isEmpty());
        }

        @Test
        @DisplayName("Поиск всех страниц с топиками")
        void findByPage() {
            Pageable topicsPageable = PageRequest.of(0, 10);
            Page<Topics> topicsPage = topicsRepository.findAll(topicsPageable);
            Assertions.assertTrue(topicsPage.getTotalElements() > 0);
        }

        @Test
        @DisplayName("Поиск всех страниц с фильтрацией по title без регистра")
        void findByTitleIgnoreCase() {
            Pageable pageable = PageRequest.of(0, 10);
            Page<Topics> topicsPage = topicsRepository.findAllByTitleContainingIgnoreCase("ЛИТЕРАТУРА 90-Х ГОДОВ", pageable);
            Assertions.assertTrue(topicsPage.getTotalElements() == 1);
            Assertions.assertEquals("Литература 90-х годов", topicsPage.getContent().get(0).getTitle());
        }

        @Test
        @DisplayName("Обновление топика")
        void updateTopics() {
            topics.setTitle("Updated Title");
            topicsRepository.save(topics);
            Topics updateTopics = topicsRepository.findById(topics.getId()).orElse(null);
            Assertions.assertNotNull(topics);
            Assertions.assertEquals("Updated Title", updateTopics.getTitle());
        }

        @Test
        @DisplayName("Удаление топика по id")
        void deleteTopicById() {
            Topics topicsDelete = new Topics();
            topics.setTitle("Литература 90-х годов");
            topics.setDescription("Топики на темы, связанные с книгами и чтением");
            topics.setParentId(null);
            topicsRepository.save(topicsDelete);
            topicsRepository.deleteById(topicsDelete.getId());
            Assertions.assertTrue(topicsRepository.findById(topicsDelete.getId()).isEmpty());
        }

        @Test
        @DisplayName("Поиск топика по вопросу")
        void findAllByQuestions() {
            List<Questions> questionsList = new ArrayList<>();
            questionsList.add(questions);
            Topics topicsFromQuestions = topicsRepository.findAllByQuestions(questionsList);
            Assertions.assertNotNull(topicsFromQuestions);
            Assertions.assertEquals(topics, topicsFromQuestions);
        }
    }

    @Nested
    @DisplayName("Тестирование репозитория вопросов")
    class questionsRepositoryTests {
        @Test
        @DisplayName("Поиск вопроса по id")
        void findById() {
            Questions findQuestions = questionsRepository.findById(questions.getId()).orElse(null);
            Assertions.assertNotNull(findQuestions);
            Assertions.assertEquals(questions.getQuestion(), findQuestions.getQuestion());
            Assertions.assertEquals(questions.getAnswer(), findQuestions.getAnswer());
            Assertions.assertEquals(questions.getTopicId(), findQuestions.getTopicId());
        }

        @Test
        @DisplayName("Поиск всех топиков")
        void findAllQuestions() {
            List<Questions> allQuestions = questionsRepository.findAll();
            Assertions.assertTrue(!allQuestions.isEmpty());
        }

        @Test
        @DisplayName("Поиск всех страниц с вопросами")
        void findByPage() {
            Pageable questionsPageable = PageRequest.of(0, 10);
            Page<Questions> questionsPage = questionsRepository.findAll(questionsPageable);
            Assertions.assertTrue(questionsPage.getTotalElements() > 0);
        }

        @Test
        @DisplayName("Поиск всех страниц с фильтрацией по questions без регистра")
        void findByQuestionsIgnoreCase() {
            Pageable pageable = PageRequest.of(0, 10);
            Page<Questions> questionsPage = questionsRepository.findAllByQuestionContainingIgnoreCase("КТО НАПИСАЛ 'Generation П'?", pageable);
            Assertions.assertTrue(questionsPage.getTotalElements() == 1);
            Assertions.assertEquals("Кто написал 'Generation П'?", questionsPage.getContent().get(0).getQuestion());
        }

        @Test
        @DisplayName("Обновление вопроса")
        void updateQuestions() {
            questions.setQuestion("Как обновить вопрос?");
            questionsRepository.save(questions);
            Questions updateQuestions = questionsRepository.findById(questions.getId()).orElse(null);
            Assertions.assertNotNull(questions);
            Assertions.assertEquals("Как обновить вопрос?", updateQuestions.getQuestion());
        }

        @Test
        @DisplayName("Удаление вопроса по id")
        void deleteQuestionById() {
            Questions questionDelete = new Questions();
            questionDelete.setQuestion("Как обновить вопрос?");
            questionDelete.setAnswer("Никак, увы");
            questionDelete.setTopicId(topics);
            questionsRepository.save(questionDelete);
            questionsRepository.deleteById(questionDelete.getId());
            Assertions.assertTrue(questionsRepository.findById(questionDelete.getId()).isEmpty());
        }

        @Test
        @DisplayName("Поиск вопросов по топику")
        void findByTopicId() {
            List<Questions> questionsFromTopics = questionsRepository.findByTopicId(topics);
            Assertions.assertTrue(!questionsFromTopics.isEmpty());
            Assertions.assertEquals(questionsFromTopics.get(0), questions);
        }
    }

    @Nested
    @DisplayName("Тестирование репозитория реакций")
    class reactionsRepositoryTests {
        @Test
        @DisplayName("Поиск реакций по id")
        void findById() {
            Reactions findReactions = reactionsRepository.findById(reactions.getId()).orElse(null);
            Assertions.assertNotNull(findReactions);
            Assertions.assertEquals(reactions.getType(), findReactions.getType());
            Assertions.assertEquals(reactions.getUser_id(), findReactions.getUser_id());
        }

        @Test
        @DisplayName("Поиск всех реакций")
        void findAllReactions() {
            List<Reactions> allReactions = reactionsRepository.findAll();
            Assertions.assertTrue(!allReactions.isEmpty());
        }

        @Test
        @DisplayName("Поиск всех страниц с реакциями")
        void findByPage() {
            Pageable reactionsPageable = PageRequest.of(0, 10);
            Page<Reactions> reactionsPage = reactionsRepository.findAll(reactionsPageable);
            Assertions.assertTrue(reactionsPage.getTotalElements() > 0);
        }

        @Test
        @DisplayName("Обновление реакций")
        void updateQuestions() {
            reactions.setType("dislike");
            reactionsRepository.save(reactions);
            Reactions updateReactions = reactionsRepository.findById(reactions.getId()).orElse(null);
            Assertions.assertNotNull(reactions);
            Assertions.assertEquals("dislike", updateReactions.getType());
        }

        @Test
        @DisplayName("Удаление реакций по id")
        void deleteReactionsById() {
            Reactions reactionsDelete = new Reactions();
            reactionsDelete.setType("like");
            reactionsDelete.setUser_id(UUID.randomUUID());
            reactionsRepository.save(reactionsDelete);
            reactionsRepository.deleteById(reactionsDelete.getId());
            Assertions.assertTrue(reactionsRepository.findById(reactionsDelete.getId()).isEmpty());
        }

        @Test
        @DisplayName("Поиск реакций по топику")
        void findAllByQuestionsId() {
            List<Reactions> reactionsFromTopics = reactionsRepository.findAllByQuestionsId(questions);
            Assertions.assertTrue(!reactionsFromTopics.isEmpty());
            Assertions.assertEquals(reactionsFromTopics.get(0).getQuestionsId(), questions);
        }
    }
}
