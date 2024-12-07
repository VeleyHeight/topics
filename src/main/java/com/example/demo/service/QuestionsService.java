package com.example.demo.service;

import com.example.demo.dto.QuestionsDTO;
import com.example.demo.model.Questions;
import com.example.demo.model.Topics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QuestionsService {
    Page<Questions> findAll(Pageable pageable);
    Page<Questions> findAllByQuestionContainingIgnoreCase(String title, Pageable pageable);
    Questions saveQuestions(QuestionsDTO questionsDTO);
    String deleteQuestions(Integer id);
    Questions updateQuestions(Questions questions);
    Questions findById(Integer id);
    Questions findByIdExtended(Integer id);
}
