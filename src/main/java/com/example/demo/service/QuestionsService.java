package com.example.demo.service;

import com.example.demo.dto.extended.ExtendedTopicsDTO;
import com.example.demo.dto.QuestionsDTO;
import com.example.demo.model.Questions;
import com.example.demo.model.Topics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;

public interface QuestionsService {
    Page<QuestionsDTO> findAll(Pageable pageable);

    Page<QuestionsDTO> findAllByQuestionContainingIgnoreCase(String title, Pageable pageable);

    QuestionsDTO saveQuestions(QuestionsDTO questionsDTO);

    QuestionsDTO deleteQuestions(Integer id);

    QuestionsDTO updateQuestions(QuestionsDTO questions);

    //todo Map/SOLID!!!!
    QuestionsDTO patchTopics(HashMap<String, String> map, Integer id);

    QuestionsDTO findById(Integer id);

    ExtendedTopicsDTO findByIdExtended(Integer id);
}
