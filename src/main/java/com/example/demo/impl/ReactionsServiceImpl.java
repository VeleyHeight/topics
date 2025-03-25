package com.example.demo.impl;

import com.example.demo.dto.ReactionsDTO;
import com.example.demo.filter.ReactionsFilter;
import com.example.demo.mapstruct.ReactionsMapper;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReactionsServiceImpl implements ReactionsService {
    private final ReactionsRepository reactionsRepository;
    private final ReactionsMapper reactionsMapper;
    private final Validator validator;

    @Override
    public List<ReactionsDTO> findAll() {
        return reactionsRepository.findAll().stream().map(reactionsMapper::toReactionsDTO).collect(Collectors.toList());
    }

    @Override
    public ReactionsDTO saveReactions(ReactionsDTO reactionsDTO) {
        Reactions reactions = reactionsMapper.toReactions(reactionsDTO);
        return reactionsMapper.toReactionsDTO(reactionsRepository.save(reactions));
    }

    @Override
    public void deleteReactions(Integer id) {
        if (reactionsRepository.existsById(id)) {
            reactionsRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reactions not found with ID: " + id);
        }
    }

    @Override
    public ReactionsDTO updateReactions(Integer id, ReactionsDTO reactionsDTO) {
        Reactions reactions = reactionsRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reactions not found with ID: " + id));
        reactionsMapper.updateWithNull(reactionsDTO, reactions);
        return reactionsMapper.toReactionsDTO(reactionsRepository.save(reactions));
    }

    @Override
    public ReactionsDTO patchTopics(ReactionsFilter reactionsFilter, Integer id) {
        Reactions reactions = reactionsRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reactions not found with ID: " + id));
        ReactionsDTO reactionsDTO = reactionsFilter.newPatchDTO();
        reactionsMapper.patchingUpdate(reactionsDTO, reactions);
        Set<ConstraintViolation<Reactions>> violations = validator.validate(reactions);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        reactionsRepository.save(reactions);
        return reactionsMapper.toReactionsDTO(reactions);
    }

    @Override
    public ReactionsDTO findById(Integer id) {
        Reactions topics = reactionsRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reactions not found with ID: " + id));
        return reactionsMapper.toReactionsDTO(topics);
    }
}
