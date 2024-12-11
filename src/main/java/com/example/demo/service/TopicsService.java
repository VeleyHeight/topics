package com.example.demo.service;

import com.example.demo.dto.extended.ExtendedTopicsDTO;
import com.example.demo.dto.topicsDTO.TopicsDTO;
import com.example.demo.dto.topicsDTO.TopicsDTOValidation;
import com.example.demo.model.Topics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TopicsService {
    Page<Topics> findAll(Pageable pageable);
    Page<Topics> findAllByTitleContainingIgnoreCase(String title, Pageable pageable);
    Topics saveTopics(TopicsDTOValidation topics);
    String deleteTopics(Integer id);
    Topics updateTopics(Topics topics);
    Topics findById(Integer id);
    ExtendedTopicsDTO findByIdExtended(Integer id);
}
