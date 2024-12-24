package com.example.demo.service;

import com.example.demo.dto.QuestionsDTO;
import com.example.demo.dto.ReactionsDTO;
import com.example.demo.model.Reactions;

import java.util.HashMap;
import java.util.List;

public interface ReactionsService {
    List<ReactionsDTO> findAll();

    ReactionsDTO saveReactions(ReactionsDTO reactionsDTO);

    ReactionsDTO deleteReactions(Integer id);

    ReactionsDTO updateReactions(ReactionsDTO reactions);

    ReactionsDTO patchTopics(HashMap<String, String> map, Integer id);

    ReactionsDTO findById(Integer id);
}
