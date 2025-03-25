package com.example.demo.service;

import com.example.demo.dto.QuestionsDTO;
import com.example.demo.dto.ReactionsDTO;
import com.example.demo.filter.ReactionsFilter;
import com.example.demo.model.Reactions;

import java.util.HashMap;
import java.util.List;

public interface ReactionsService {
    List<ReactionsDTO> findAll();

    ReactionsDTO saveReactions(ReactionsDTO reactionsDTO);

    void deleteReactions(Integer id);

    ReactionsDTO updateReactions(Integer id, ReactionsDTO reactions);

    ReactionsDTO patchTopics(ReactionsFilter filter, Integer id);

    ReactionsDTO findById(Integer id);
}
