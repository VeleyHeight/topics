package com.example.demo.service;

import com.example.demo.dto.TopicsDTO;
import com.example.demo.dto.extended.ExtendedTopicsDTO;
import com.example.demo.model.Topics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;

public interface TopicsService {
    Page<Topics> findAll(Pageable pageable);
    Page<Topics> findAllByTitleContainingIgnoreCase(String title, Pageable pageable);
    Topics saveTopics(TopicsDTO topics);
    Topics deleteTopics(Integer id);
    Topics updateTopics(TopicsDTO topics);
    Topics patchTopics(HashMap<String,String> map, Integer id);
    Topics findById(Integer id);
    ExtendedTopicsDTO findByIdExtended(Integer id);
}
