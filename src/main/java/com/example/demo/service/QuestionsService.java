package com.example.demo.service;

import com.example.demo.dto.extended.ExtendedTopicsDTO;
import com.example.demo.dto.QuestionsDTO;
import com.example.demo.filter.QuestionsFilter;
import com.example.demo.model.Questions;
import com.example.demo.model.Topics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;

public interface QuestionsService {
    Page<QuestionsDTO> findAll(QuestionsFilter questionsFilter, Pageable pageable);

    QuestionsDTO saveQuestions(QuestionsDTO questionsDTO);

    void deleteQuestions(Integer id);

    QuestionsDTO updateQuestions(Integer id, QuestionsDTO questions);

    QuestionsDTO patchTopics(QuestionsFilter questionsFilter, Integer id);

    QuestionsDTO findById(Integer id);

    ExtendedTopicsDTO findByIdExtended(Integer id);
}
