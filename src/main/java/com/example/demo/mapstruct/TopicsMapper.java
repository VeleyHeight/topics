package com.example.demo.mapstruct;

import com.example.demo.dto.TopicsDTO;
import com.example.demo.model.Topics;
import com.example.demo.repository.TopicsRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
uses = {
        TopicsMapperUtils.class
},
imports = {Timestamp.class})
public interface TopicsMapper {

    @Mapping(target = "parentId", qualifiedByName = {"TopicsMapperUtils","createTopicsFromParent"}, source = "parentId")
    @Mapping(target = "updated_at", expression = "java(new Timestamp(System.currentTimeMillis()))")
    @Mapping(target = "created_at", ignore = true)
    Topics toTopics(TopicsDTO topicsDTO);

    @Mapping(target = "parentId", source = "parentId.id")
    TopicsDTO toTopicsDTO(Topics topics);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created_at", ignore = true)
    @Mapping(target = "updated_at", expression = "java(new Timestamp(System.currentTimeMillis()))")
    @Mapping(target = "parentId", qualifiedByName = {"TopicsMapperUtils","createTopicsFromParent"}, source = "parentId")
    Topics partialUpdate(TopicsDTO topicsDTO, @MappingTarget Topics topics);
}
