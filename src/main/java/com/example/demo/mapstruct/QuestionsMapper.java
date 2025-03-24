package com.example.demo.mapstruct;

import com.example.demo.dto.QuestionsDTO;
import com.example.demo.model.Questions;
import org.mapstruct.*;

import java.sql.Timestamp;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {TopicsMapperUtils.class},
        imports = {Timestamp.class})
public interface QuestionsMapper {
    @Mapping(target = "topicId", qualifiedByName = {"TopicsMapperUtils", "createTopicsFromParent"})
    @Mapping(target = "updated_at", expression = "java(new Timestamp(System.currentTimeMillis()))")
    @Mapping(target = "created_at", ignore = true)
    Questions toQuestions(QuestionsDTO questionsDTO);

    @Mapping(target = "topicId", source = "topicId.id")
    QuestionsDTO toQuestionsDTO(Questions questions);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created_at", ignore = true)
    @Mapping(target = "updated_at", expression = "java(new Timestamp(System.currentTimeMillis()))")
    @Mapping(target = "topicId", qualifiedByName = {"TopicsMapperUtils", "createTopicsFromParent"}, source = "topicId")
    Questions updateWithNull(QuestionsDTO questionsDTO, @MappingTarget Questions questions);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created_at", ignore = true)
    @Mapping(target = "updated_at", expression = "java(new Timestamp(System.currentTimeMillis()))")
    @Mapping(target = "topicId", qualifiedByName = {"TopicsMapperUtils", "createTopicsFromParent"}, source = "topicId")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Questions patchingUpdate(QuestionsDTO questionsDTO, @MappingTarget Questions questions);
}
