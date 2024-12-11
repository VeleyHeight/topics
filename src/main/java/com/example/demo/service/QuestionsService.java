package com.example.demo.service;

import com.example.demo.dto.extended.ExtendedTopicsDTO;
import com.example.demo.dto.QuestionsDTO;
import com.example.demo.model.Questions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionsService {
    Page<Questions> findAll(Pageable pageable);
    Page<Questions> findAllByQuestionContainingIgnoreCase(String title, Pageable pageable);
    Questions saveQuestions(QuestionsDTO questionsDTO);
    String deleteQuestions(Integer id);
    Questions updateQuestions(Questions questions);
    Questions findById(Integer id);
    ExtendedTopicsDTO findByIdExtended(Integer id);
}
