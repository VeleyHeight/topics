package com.example.demo.impl;

import com.example.demo.model.Topics;
import com.example.demo.repository.TopicsRepository;
import com.example.demo.service.TopicsService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class topicsServiceImpl implements TopicsService {
    TopicsRepository topicsRepository;
    @Override
    public Page<Topics> findAll(Pageable pageable) {
        return topicsRepository.findAll(pageable);
    }

    @Override
    public Page<Topics> findAllByTitleContainingIgnoreCase(String title, Pageable pageable) {
        return topicsRepository.findAllByTitleContainingIgnoreCase(title, pageable);
    }

    @Override
    public Topics saveTopics(Topics topics) {
        return topicsRepository.save(topics);
    }

    @Override
    public String deleteTopics(Integer id) {
        topicsRepository.deleteById(id);
        return "Topic deleted";
    }

    @Override
    public Topics updateTopics(Topics topics) {
        return topicsRepository.save(topics);
    }

    @Override
    public Topics findById(Integer id) {
        return topicsRepository.findById(id).orElse(null);
    }

    @Override
    public Topics findByIdExtended(Integer id) {
        return null;
    }
}
