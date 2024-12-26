package com.example.demo.impl;

import com.example.demo.converter.QuestionsConverter;
import com.example.demo.converter.ReactionsConverter;
import com.example.demo.converter.TopicsConverter;
import com.example.demo.dto.ReactionsDTO;
import com.example.demo.dto.extended.ExtendedQuestions;
import com.example.demo.dto.extended.ExtendedTopicsDTO;
import com.example.demo.dto.QuestionsDTO;
import com.example.demo.model.Questions;
import com.example.demo.model.Topics;
import com.example.demo.repository.QuestionsRepository;
import com.example.demo.repository.ReactionsRepository;
import com.example.demo.repository.TopicsRepository;
import com.example.demo.service.QuestionsService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class QuestionsServiceImpl implements QuestionsService {
    private final ReactionsConverter reactionsConverter;
    private QuestionsRepository questionsRepository;
    private TopicsRepository topicsRepository;
    private ReactionsRepository reactionsRepository;
    private TopicsConverter topicsConverter;
    private QuestionsConverter questionsConverter;
    private Validator validator;

    @Override
    public Page<QuestionsDTO> findAll(Pageable pageable) {
        return questionsConverter.convertToDTOPage(questionsRepository.findAll(pageable));
    }

    @Override
    public Page<QuestionsDTO> findAllByQuestionContainingIgnoreCase(String title, Pageable pageable) {
        return questionsConverter.convertToDTOPage(questionsRepository.findAllByQuestionContainingIgnoreCase(title, pageable));
    }

    @Override
    public QuestionsDTO saveQuestions(QuestionsDTO questionsDTO) {
        Questions questions = new Questions();
        Optional<Topics> optionalTopic = topicsRepository.findById(questionsDTO.getTopicId());
        questions.setQuestion(questionsDTO.getQuestion());
        questions.set_popular(questionsDTO.is_popular());
        questions.setAnswer(questionsDTO.getAnswer());
        questions.setTopicId(optionalTopic.get());
        return questionsConverter.convertToDTO(questionsRepository.save(questions));
    }

    @Override
    public QuestionsDTO updateQuestions(QuestionsDTO questions) {
        Optional<Questions> optionalQuestions = questionsRepository.findById(questions.getId());
        optionalQuestions.get().setQuestion(questions.getQuestion());
        optionalQuestions.get().setAnswer(questions.getAnswer());
        optionalQuestions.get().set_popular(questions.is_popular());
        optionalQuestions.get().setTopicId(topicsRepository.findById(questions.getTopicId()).get());
        return questionsConverter.convertToDTO(questionsRepository.save(optionalQuestions.get()));
    }

    @Override
    public QuestionsDTO patchTopics(HashMap<String, String> body, Integer id) {
        Optional<Questions> questions = questionsRepository.findById(id);
        QuestionsDTO questionsDTO = questionsConverter.convertToDTO(questions.get());
        if (body.containsKey("questions") && body.get("questions") != null) {
            questionsDTO.setQuestion(body.get("questions"));
        }
        if (body.containsKey("answer") && body.get("answer") != null) {
            questionsDTO.setAnswer(body.get("answer"));
        }
        if (body.containsKey("is_popular") && body.get("is_popular") != null) {
            questionsDTO.set_popular(Boolean.parseBoolean(body.get("is_popular")));
        }
        if (body.containsKey("topicId") && body.get("topicId") != null) {
            questionsDTO.setTopicId(Integer.valueOf(body.get("topicId")));
        }
        Set<ConstraintViolation<QuestionsDTO>> violations = validator.validate(questionsDTO);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        questions.get().setQuestion(questionsDTO.getQuestion());
        questions.get().setAnswer(questionsDTO.getAnswer());
        questions.get().setTopicId(topicsRepository.findById(id).get());
        questions.get().set_popular(questionsDTO.is_popular());
        questionsRepository.save(questions.get());
        return questionsDTO;
    }

    @Override
    public QuestionsDTO findById(Integer id) {
        Questions questions = questionsRepository.findById(id).orElse(null);
        if (questions == null) {
            return null;
        } else {
            return questionsConverter.convertToDTO(questions);
        }
    }

    @Override
    public QuestionsDTO deleteQuestions(Integer id) {
        Questions questions = questionsRepository.findById(id).get();
        questionsRepository.deleteById(id);
        return questionsConverter.convertToDTO(questions);
    }

    @Override
    public ExtendedTopicsDTO findByIdExtended(Integer id) {
        List<Questions> questionsList = new ArrayList<>();
        Optional<Questions> optionalQuestions = questionsRepository.findById(id);
        Topics topics;
        if (optionalQuestions.isPresent()) {
            questionsList.add(optionalQuestions.get());
            topics = topicsRepository.findAllByQuestions(questionsList);
            List<ExtendedQuestions> extendedQuestionsList = new ArrayList<>();
            List<ReactionsDTO> reactionsList = reactionsConverter.convertToListDTO(reactionsRepository.findAllByQuestionsId(optionalQuestions.get()));
            extendedQuestionsList.add(new ExtendedQuestions(optionalQuestions.get().getId(), optionalQuestions.get().getQuestion(), optionalQuestions.get().getAnswer(), optionalQuestions.get().is_popular(), reactionsList));
            ExtendedTopicsDTO extendedTopicsDTO = new ExtendedTopicsDTO(topicsConverter.convertToDTO(topics), extendedQuestionsList);
            return extendedTopicsDTO;
        } else {
            return null;
        }
    }

}
