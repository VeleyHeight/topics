package com.example.demo.mapstruct;

import com.example.demo.dto.QuestionsDTO;
import com.example.demo.model.Questions;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface QuestionsMapper {
    @Mapping(target = "topicId", ignore = true)
    Questions toQuestions(QuestionsDTO questionsDTO);
    @Mapping(target = "topicId", source = "topicId.id")
    QuestionsDTO toQuestionsDTO(Questions questions);
}
