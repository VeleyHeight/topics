package com.example.demo.service;

import com.example.demo.dto.extended.ExtendedTopicsDTO;
import com.example.demo.dto.topicsDTO.TopicsDTO;
import com.example.demo.dto.topicsDTO.TopicsDTOValidation;
import com.example.demo.dto.topicsDTO.UpdateTopicdDTOValidation;
import com.example.demo.model.Topics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TopicsService {
    Page<Topics> findAll(Pageable pageable);
    Page<Topics> findAllByTitleContainingIgnoreCase(String title, Pageable pageable);
    Topics saveTopics(TopicsDTOValidation topics);
    Topics deleteTopics(Integer id);
    Topics updateTopics(TopicsDTOValidation topics,Integer id);
    Topics patchTopics(UpdateTopicdDTOValidation topics, Integer id);
    Topics findById(Integer id);
    ExtendedTopicsDTO findByIdExtended(Integer id);
}
