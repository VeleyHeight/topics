package com.example.demo.impl;

import com.example.demo.dto.QuestionsDTO;
import com.example.demo.dto.ReactionsDTO;
import com.example.demo.model.Questions;
import com.example.demo.model.Reactions;
import com.example.demo.model.Topics;
import com.example.demo.repository.QuestionsRepository;
import com.example.demo.repository.ReactionsRepository;
import com.example.demo.service.ReactionsService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class reactionsServiceImpl implements ReactionsService {
    private final ReactionsRepository reactionsRepository;
    private final QuestionsRepository questionsRepository;
    private Validator validator;
    public List<ReactionsDTO> convertToListDto(List<Reactions> reactionsList) {
        if (reactionsList == null) {
            return null;
        }
        List<ReactionsDTO> dto = new ArrayList<>(reactionsList.size());
        for (Reactions reactions : reactionsList) {
            dto.add(convertToDto(reactions));
        }
        return dto;
    }
    public ReactionsDTO convertToDto(Reactions reactions) {
        if (reactions == null) {
            return null;
        }
        ReactionsDTO dto = new ReactionsDTO();
        dto.setId(reactions.getId());
        dto.setUser_id(reactions.getUser_id() != null ? reactions.getUser_id().toString() : null);
        dto.setType(reactions.getType());
        dto.setQuestionsId(reactions.getQuestionsId() != null ? reactions.getQuestionsId().getId() : null);
        return dto;
    }
    @Override
    public List<ReactionsDTO> findAll() {
        return convertToListDto(reactionsRepository.findAll());
    }

    @Override
    public ReactionsDTO saveReactions(ReactionsDTO reactionsDTO) {
        Reactions reactions = new Reactions();
        reactions.setUser_id(reactions.getUser_id());
        reactions.setType(reactionsDTO.getType());
        reactions.setQuestionsId(questionsRepository.findById(reactionsDTO.getQuestionsId()).get());
        return convertToDto(reactionsRepository.save(reactions));
    }

    @Override
    public ReactionsDTO deleteReactions(Integer id) {
        Reactions reactions = reactionsRepository.findById(id).get();
        questionsRepository.deleteById(id);
        return convertToDto(reactions);
    }

    @Override
    public ReactionsDTO updateReactions(ReactionsDTO reactionsDTO) {
        Reactions reactions = reactionsRepository.findById(reactionsDTO.getId()).get();
        reactions.setType(reactionsDTO.getType());
        reactions.setUser_id(UUID.fromString(reactionsDTO.getUser_id()));
        return convertToDto(reactionsRepository.save(reactions));
    }

    @Override
    public ReactionsDTO patchTopics(HashMap<String, String> body, Integer id) {
        Reactions reactions = reactionsRepository.findById(id).get();
        ReactionsDTO reactionsDTO = convertToDto(reactions);
        if (body.containsKey("user_id") && body.get("user_id") != null){
            reactionsDTO.setUser_id(body.get("user_id"));
        }
        if (body.containsKey("type") && body.get("type") != null){
            reactionsDTO.setType(body.get("type"));
        }
        if (body.containsKey("questionsId") && body.get("questionsId") != null){
            reactionsDTO.setQuestionsId(Integer.valueOf(body.get("questionsId")));
        }
        Set<ConstraintViolation<ReactionsDTO>> violations = validator.validate(reactionsDTO);
        if (!violations.isEmpty()){
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
        if(reactions == null){
            return null;
        }
        return convertToDto(reactions);
    }
}
