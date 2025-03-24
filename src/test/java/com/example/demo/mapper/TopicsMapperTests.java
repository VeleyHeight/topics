package com.example.demo.mapper;

import com.example.demo.dto.TopicsDTO;
import com.example.demo.mapstruct.TopicsMapper;
import com.example.demo.model.Topics;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
class TopicsMapperTests {
    @Autowired
    private TopicsMapper topicsMapper;
    private TopicsDTO topicsDTO;
    private Topics topics;


    @Test
    @DisplayName("Тестирование маппинга топика в DTO")
    public void topicsToDtoMapper(){
        topicsDTO = new TopicsDTO("Тестовый заголовок", "Тестовое описание", 1, null, null);
        topics = Topics.builder().title("Название топика").description("Описание топика").parentId(null).build();
        topics = topicsMapper.toTopics(topicsDTO);
        Assertions.assertEquals(topicsDTO.title(),topics.getTitle());
        Assertions.assertEquals(topicsDTO.description(),topics.getDescription());
        Assertions.assertEquals(topicsDTO.parentId(),topics.getParentId().getId());
        System.out.println("Title: "+topics.getTitle()+", Description: "+topics.getDescription()+", Parent Id: "+topics.getParentId().getId());
    }

    @Test
    @DisplayName("Тестирование маппинга DTO в топик")
    public void DtoToTopicsMapper(){
        topicsDTO = new TopicsDTO("Тестовый заголовок", "Тестовое описание", 1, null, null);
        topics = Topics.builder().title("Название топика").description("Описание топика").parentId(null).build();
        topicsDTO = topicsMapper.toTopicsDTO(topics);
        Assertions.assertEquals(topics.getTitle(),topicsDTO.title());
        Assertions.assertEquals(topics.getDescription(),topicsDTO.description());
        Assertions.assertEquals(topics.getParentId(),topicsDTO.parentId());
        System.out.println("Title: "+topicsDTO.title()+", Description: "+topicsDTO.description()+", Parent Id: "+topicsDTO.parentId());
    }

    @Test
    @DisplayName("Тестирование маппинга update топика")
    public void updateTopics(){
        topicsDTO = new TopicsDTO("Тестовый заголовок", "Тестовое описание", 1, null, null);
        topics = Topics.builder().title("Название топика").description("Описание топика").parentId(null).build();
        topics = topicsMapper.updateWithNull(topicsDTO, topics);
        Assertions.assertEquals(topicsDTO.title(),topics.getTitle());
        Assertions.assertEquals(topicsDTO.description(),topics.getDescription());
        Assertions.assertEquals(topicsDTO.parentId(),topics.getParentId().getId());
        System.out.println("Title: "+topics.getTitle()+", Description: "+topics.getDescription()+", Parent Id: "+topics.getParentId().getId());
    }

    @Test
    @DisplayName("Тестирование маппинга patch топика")
    public void patchTopics(){
        topicsDTO = new TopicsDTO(null, "Тестовое описание", 1, null, null);
        topics = Topics.builder().title("Название топика").description("Описание топика").parentId(null).build();
        topics = topicsMapper.patchingUpdate(topicsDTO, topics);
        Assertions.assertEquals("Название топика",topics.getTitle());
        Assertions.assertEquals(topicsDTO.description(),topics.getDescription());
        Assertions.assertEquals(1,topics.getParentId().getId());
        System.out.println("Title: "+topics.getTitle()+", Description: "+topics.getDescription()+", Parent Id: "+topics.getParentId().getId());
    }
}
