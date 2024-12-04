package com.example.demo.service;

import com.example.demo.model.Topics;

import java.util.List;

public interface TopicsService {
    List<Topics> findAll();
    Topics saveTopics(Topics topics);
    void deleteTopics(Integer id);
    Topics updateTopics(Topics topics);
}
