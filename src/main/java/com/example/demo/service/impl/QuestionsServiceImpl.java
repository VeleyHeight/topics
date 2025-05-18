package com.example.demo.service.impl;

import com.example.demo.dto.ReactionsDTO;
import com.example.demo.dto.extended.ExtendedQuestions;
import com.example.demo.dto.extended.ExtendedTopicsDTO;
import com.example.demo.dto.QuestionsDTO;
import com.example.demo.filter.QuestionsFilter;
import com.example.demo.mapstruct.QuestionsMapper;
import com.example.demo.mapstruct.ReactionsMapper;
import com.example.demo.mapstruct.TopicsMapper;
import com.example.demo.model.Questions;
import com.example.demo.model.Topics;
import com.example.demo.repository.QuestionsRepository;
import com.example.demo.repository.ReactionsRepository;
import com.example.demo.repository.TopicsRepository;
import com.example.demo.service.QuestionsService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionsServiceImpl implements QuestionsService {

    private final QuestionsRepository questionsRepository;
    private final ReactionsMapper reactionsMapper;
    private final TopicsRepository topicsRepository;
    private final ReactionsRepository reactionsRepository;
    private final TopicsMapper topicsMapper;
    private final QuestionsMapper questionsMapper;
    private final Validator validator;

    @Override
    public Page<QuestionsDTO> findAll(QuestionsFilter questionsFilter, Pageable pageable) {
        return questionsRepository.findAll(questionsFilter.specification(), pageable).map(questionsMapper::toQuestionsDTO);
    }

    @Override
    public QuestionsDTO saveQuestions(QuestionsDTO questionsDTO) {
        Questions questions = questionsMapper.toQuestions(questionsDTO);
        return questionsMapper.toQuestionsDTO(questionsRepository.save(questions));
    }

    @Override
    public QuestionsDTO updateQuestions(Integer id, QuestionsDTO questionsDTO) {
        Questions questions = questionsRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Questions not found with ID: " + id));
        questionsMapper.updateWithNull(questionsDTO, questions);
        return questionsMapper.toQuestionsDTO(questionsRepository.save(questions));
    }

    @Override
    public QuestionsDTO patchTopics(QuestionsFilter questionsFilter, Integer id) {
        Questions questions = questionsRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Questions not found with ID: " + id));
        questionsMapper.patchingUpdate(questionsFilter.newPatchDTO(), questions);
        Set<ConstraintViolation<Questions>> violations = validator.validate(questions);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        questionsRepository.save(questions);
        return questionsMapper.toQuestionsDTO(questions);
    }

    @Override
    public QuestionsDTO findById(Integer id) {
        Questions questions = questionsRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Questions not found with ID: " + id));
        return questionsMapper.toQuestionsDTO(questions);
    }

    @Override
    public void deleteQuestions(Integer id) {
        if (questionsRepository.existsById(id)) {
            questionsRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Questions not found with ID: " + id);
        }
    }

    @Override
    public ExtendedTopicsDTO findByIdExtended(Integer id) {
        List<Questions> questionsList = new ArrayList<>();
        Questions questions = questionsRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Questions not found with ID: " + id));
        Topics topics;
        questionsList.add(questions);
        topics = topicsRepository.findAllByQuestions(questionsList);
        List<ExtendedQuestions> extendedQuestionsList = new ArrayList<>();
        List<ReactionsDTO> reactionsList = reactionsRepository.findAllByQuestionsId(questions).stream().map(reactionsMapper::toReactionsDTO).collect(Collectors.toList());
        extendedQuestionsList.add(new ExtendedQuestions(questions.getId(), questions.getQuestion(), questions.getAnswer(), questions.getIs_popular(), reactionsList));
        ExtendedTopicsDTO extendedTopicsDTO = new ExtendedTopicsDTO(topics.getId(), topicsMapper.toTopicsDTO(topics), extendedQuestionsList);
        return extendedTopicsDTO;
    }

}
