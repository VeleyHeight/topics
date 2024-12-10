package com.example.demo.impl;

import com.example.demo.dto.ReactionsDTO;
import com.example.demo.model.Questions;
import com.example.demo.model.Reactions;
import com.example.demo.model.Topics;
import com.example.demo.repository.QuestionsRepository;
import com.example.demo.repository.ReactionsRepository;
import com.example.demo.service.ReactionsService;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class reactionsServiceImpl implements ReactionsService {
    private final ReactionsRepository reactionsRepository;
    private final QuestionsRepository questionsRepository;

    @Override
    public List<Reactions> findAll() {
        return reactionsRepository.findAll();
    }

    @Override
    public Reactions saveReactions(ReactionsDTO reactionsDTO) {
        Reactions reactions = new Reactions();
        reactions.setUser_id(reactions.getUser_id());
        reactions.setType(reactionsDTO.getType());
        reactions.setQuestionsId(null);
        if (reactionsDTO.getQuestionsId() != null) {
            Optional<Questions> reactionsOptional = questionsRepository.findById(reactionsDTO.getQuestionsId());
            if (reactionsOptional.isPresent()) {
                reactions.setQuestionsId(reactionsOptional.get());
            }
            else {
                throw new EmptyResultDataAccessException(1);
            }
        }
        return reactionsRepository.save(reactions);
    }

    @Override
    public String deleteReactions(Integer id) {
        reactionsRepository.deleteById(id);
        return "Reactions deleted";
    }

    @Override
    public Reactions updateReactions(Reactions reactions) {
        return reactionsRepository.save(reactions);
    }

    @Override
    public Reactions findById(Integer id) {
        return reactionsRepository.findById(id).orElse(null);
    }
}
