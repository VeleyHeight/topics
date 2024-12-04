package com.example.demo.controller;

import com.example.demo.model.Topics;
import com.example.demo.service.TopicsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

    @RestController
    @AllArgsConstructor
    @RequestMapping("/topics")
    public class TopicsCRUDController {
        private final TopicsService topicsService;
        @GetMapping
        public List<Topics> getAllTopics() {
            return topicsService.findAll();
        }
    
    }
