package com.example.demo.controller;

import com.example.demo.model.Topics;
import com.example.demo.repository.TopicsRepository;
import com.example.demo.service.TopicsService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

import java.util.List;

    @RestController
    @AllArgsConstructor
    @RequestMapping("/topics")
    public class TopicsCRUDController {
        private final TopicsService topicsService;
        private final TopicsRepository topicsRepository;

        @GetMapping
        public Page<Topics> getAllTopics(@RequestParam(required = false) String title,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size) {
            if (title != null && !title.isEmpty()) {
                return topicsRepository.findAllByTitleContainingIgnoreCase(title, PageRequest.of(page, size));
            }
            return topicsService.findAll(PageRequest.of(page, size));
        }
        @PostMapping
        public Topics createTopics(@RequestBody Topics topics) {
            return topicsService.saveTopics(topics);
        }
        @PutMapping("update_topic")
        public Topics updateTopics(@RequestBody Topics topics) {
            return topicsService.updateTopics(topics);
        }
        @DeleteMapping("delete_topic/{id}")
        public String deleteTopics(@PathVariable Topics id) {

            return topicsService.deleteTopics(id.getId());
        }
    }
