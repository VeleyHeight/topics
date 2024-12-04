package com.example.demo.impl;

import com.example.demo.model.Topics;
import com.example.demo.repository.TopicsRepository;
import com.example.demo.service.TopicsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class topicsServiceImpl implements TopicsService {
    TopicsRepository topicsRepository;
    @Override
    public List<Topics> findAll() {
        return topicsRepository.findAll();
    }

    @Override
    public Topics saveTopics(Topics topics) {
        return topicsRepository.save(topics);
    }

    @Override
    public void deleteTopics(Integer id) {
        topicsRepository.deleteById(id);
    }

    @Override
    public Topics updateTopics(Topics topics) {

        return topicsRepository.save(topics);
    }
}
