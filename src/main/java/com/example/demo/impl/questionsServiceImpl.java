package com.example.demo.impl;

import com.example.demo.dto.QuestionsDTO;
import com.example.demo.model.Questions;
import com.example.demo.model.Topics;
import com.example.demo.repository.QuestionsRepository;
import com.example.demo.repository.TopicsRepository;
import com.example.demo.service.QuestionsService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class questionsServiceImpl implements QuestionsService {
    private QuestionsRepository questionsRepository;
    private TopicsRepository topicsRepository;

    @Override
    public Page<Questions> findAll(Pageable pageable) {
        return questionsRepository.findAll(pageable);
    }

    @Override
    public Page<Questions> findAllByQuestionContainingIgnoreCase(String title, Pageable pageable) {
        return questionsRepository.findAllByQuestionContainingIgnoreCase(title, pageable);
    }

    @Override
    public Questions saveQuestions(QuestionsDTO questionsDTO) {
        Questions questions = new Questions();
        Optional<Topics> optionalTopic = topicsRepository.findById(questionsDTO.getTopicId());
        if (optionalTopic.isPresent()){
            questions.setQuestion(questionsDTO.getQuestion());
            questions.set_popular(questionsDTO.is_popular());
            questions.setAnswer(questionsDTO.getAnswer());
            questions.setTopicId(optionalTopic.get());
            return questionsRepository.save(questions);
        }
        else{
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String deleteQuestions(Integer id) {
        questionsRepository.deleteById(id);
        return "Questions deleted successfully";
    }

    @Override
    public Questions updateQuestions(Questions questions) {
        return questionsRepository.save(questions);
    }

    @Override
    public Questions findById(Integer id) {
        return questionsRepository.findById(id).orElse(null);
    }

}
