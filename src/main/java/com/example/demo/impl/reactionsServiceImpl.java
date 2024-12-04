package com.example.demo.impl;

import com.example.demo.model.Reactions;
import com.example.demo.repository.ReactionsRepository;
import com.example.demo.service.ReactionsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class reactionsServiceImpl implements ReactionsService {
    private final ReactionsRepository reactionsRepository;
    @Override
    public List<Reactions> findAll() {
        return reactionsRepository.findAll();
    }

    @Override
    public Reactions saveReactions(Reactions reactions) {
        return reactionsRepository.save(reactions);
    }

    @Override
    public void deleteReactions(Integer id) {
        reactionsRepository.deleteById(id);
    }

    @Override
    public Reactions updateReactions(Reactions reactions) {
        return reactionsRepository.save(reactions);
    }
}