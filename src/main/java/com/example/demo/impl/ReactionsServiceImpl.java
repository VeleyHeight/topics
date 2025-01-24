package com.example.demo.impl;

import com.example.demo.converter.ReactionsConverter;
import com.example.demo.dto.ReactionsDTO;
import com.example.demo.model.Reactions;
import com.example.demo.repository.QuestionsRepository;
import com.example.demo.repository.ReactionsRepository;
import com.example.demo.service.ReactionsService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

//todo Исправь заменчания в topic service, увидишь аналогичные ошибки имплементации сервисов для question, reaction
@Service
@AllArgsConstructor
public class ReactionsServiceImpl implements ReactionsService {
    private final ReactionsRepository reactionsRepository;
    private final QuestionsRepository questionsRepository;
    private Validator validator;
    private ReactionsConverter reactionsConverter;


    @Override
    public List<ReactionsDTO> findAll() {
        return reactionsConverter.convertToListDTO(reactionsRepository.findAll());
    }

    @Override
    public ReactionsDTO saveReactions(ReactionsDTO reactionsDTO) {
        Reactions reactions = new Reactions();
        reactions.setUser_id(UUID.fromString(reactionsDTO.getUser_id()));
        reactions.setType(reactionsDTO.getType());
        reactions.setQuestionsId(questionsRepository.findById(reactionsDTO.getQuestionsId()).get());
        return reactionsConverter.convertToDTO(reactionsRepository.save(reactions));
    }

    @Override
    public ReactionsDTO deleteReactions(Integer id) {
        Reactions reactions = reactionsRepository.findById(id).get();
        questionsRepository.deleteById(id);
        return reactionsConverter.convertToDTO(reactions);
    }

    @Override
    public ReactionsDTO updateReactions(ReactionsDTO reactionsDTO) {
        Reactions reactions = reactionsRepository.findById(reactionsDTO.getId()).get();
        reactions.setType(reactionsDTO.getType());
        reactions.setUser_id(UUID.fromString(reactionsDTO.getUser_id()));
        return reactionsConverter.convertToDTO(reactionsRepository.save(reactions));
    }

    @Override
    public ReactionsDTO patchTopics(HashMap<String, String> body, Integer id) {
        Reactions reactions = reactionsRepository.findById(id).get();
        ReactionsDTO reactionsDTO = reactionsConverter.convertToDTO(reactions);
        if (body.containsKey("user_id") && body.get("user_id") != null) {
            reactionsDTO.setUser_id(body.get("user_id"));
        }
        if (body.containsKey("type") && body.get("type") != null) {
            reactionsDTO.setType(body.get("type"));
        }
        if (body.containsKey("questionsId") && body.get("questionsId") != null) {
            reactionsDTO.setQuestionsId(Integer.valueOf(body.get("questionsId")));
        }
        Set<ConstraintViolation<ReactionsDTO>> violations = validator.validate(reactionsDTO);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        reactions.setUser_id(UUID.fromString(reactionsDTO.getUser_id()));
        reactions.setType(reactionsDTO.getType());
        reactions.setQuestionsId(questionsRepository.findById(reactionsDTO.getQuestionsId()).get());
        reactionsRepository.save(reactions);
        return reactionsDTO;
    }

    @Override
    public ReactionsDTO findById(Integer id) {
        Reactions reactions = reactionsRepository.findById(id).orElse(null);
        if (reactions == null) {
            return null;
        }
        return reactionsConverter.convertToDTO(reactions);
    }
}
