package com.example.demo.impl;

import com.example.demo.model.Questions;
import com.example.demo.repository.QuestionsRepository;
import com.example.demo.service.QuestionsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class questionsServiceImpl implements QuestionsService {
    private QuestionsRepository questionsRepository;
    @Override
    public List<Questions> findAll() {
        return questionsRepository.findAll();
    }

    @Override
    public Questions saveQuestions(Questions questions) {
        return questionsRepository.save(questions);
    }

    @Override
    public void deleteQuestions(Integer id) {
        questionsRepository.deleteById(id);
    }

    @Override
    public Questions updateQuestions(Questions questions) {
        return questionsRepository.save(questions);
    }
}
