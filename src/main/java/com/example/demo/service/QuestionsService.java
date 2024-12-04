package com.example.demo.service;

import com.example.demo.model.Questions;

import java.util.List;

public interface QuestionsService {
    List<Questions> findAll();
    Questions saveQuestions(Questions questions);
    void deleteQuestions(Integer id);
    Questions updateQuestions(Questions questions);
}
