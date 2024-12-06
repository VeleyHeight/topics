package com.example.demo.service;

import com.example.demo.model.Topics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TopicsService {
    Page<Topics> findAll(Pageable pageable);
    Page<Topics> findAllByTitleContainingIgnoreCase(String title, Pageable pageable);
    Topics saveTopics(Topics topics);
    String deleteTopics(Integer id);
    Topics updateTopics(Topics topics);
    Topics findById(Integer id);
    Topics findByIdExtended(Integer id);
}
