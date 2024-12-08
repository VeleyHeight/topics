package com.example.demo.controller;

import com.example.demo.dto.ExtendedDTO;
import com.example.demo.dto.TopicsDTO;
import com.example.demo.model.Topics;
import com.example.demo.repository.TopicsRepository;
import com.example.demo.service.TopicsService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

    @RestController
    @AllArgsConstructor
    @RequestMapping("/topics")
    public class TopicsCRUDController {
        private final TopicsService topicsService;
        @GetMapping
        public ResponseEntity<Page<Topics>> getAllTopics(@RequestParam(required = false) String title,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size) {
            if (title != null && !title.isEmpty()) {
                return ResponseEntity.ok(topicsService.findAllByTitleContainingIgnoreCase(title, PageRequest.of(page, size)));
            }
            return ResponseEntity.ok(topicsService.findAll(PageRequest.of(page, size)));
        }
        @GetMapping("/{id}")
        public ResponseEntity<Topics> getTopicsById(@PathVariable Integer id) {
            if (topicsService.findById(id) == null){
                return ResponseEntity.notFound().build();
            }
            else {
                return ResponseEntity.ok(topicsService.findById(id));
            }
        }
        @PostMapping
        public ResponseEntity<Topics> createTopics(@RequestBody TopicsDTO topicsDTO) {
            try {
                return ResponseEntity.ok(topicsService.saveTopics(topicsDTO));
            }
            catch (Exception e){
                return ResponseEntity.notFound().build();
            }
        }
        @PutMapping("/{id}")
        public ResponseEntity<Topics> updateTopics(@PathVariable Integer id, @RequestBody Topics topics) {
            Topics topicsFind = topicsService.findById(id);
            if (topicsFind != null){
                if (topics.getParent_id().toString().equals(topicsFind.getId().toString())){
                    return ResponseEntity.badRequest().build();
                }
                topicsFind.setTitle(topics.getTitle());
                topicsFind.setDescription(topics.getDescription());
                topicsFind.setParent_id(topics.getParent_id());
                return ResponseEntity.ok(topicsService.updateTopics(topics));
            }
            else{
                return ResponseEntity.notFound().build();
            }
        }
        @PatchMapping("/{id}")
        public ResponseEntity<Topics> patchTopics(@PathVariable Integer id, @RequestBody Map<String,Object> body) {
            if (topicsService.findById(id) == null) {
                return ResponseEntity.notFound().build();
            }
            try {
                Topics topics = topicsService.findById(id);
                body.forEach((key, value) -> {
                    switch (key) {
                        case "title":
                            topics.setTitle((String) value);
                            break;
                        case "description":
                            topics.setDescription((String) value);
                            break;
                        case "parent_id":
                            if (id.equals(topics.getId())){
                                throw new IllegalArgumentException("Recursion id: " + id);
                            }
                            topics.setParent_id((Topics) value);
                            break;
                        default:
                            throw new IllegalArgumentException("Invalid field: " + key);
                    }
                });
                return ResponseEntity.ok(topicsService.updateTopics(topics));
            }
            catch (Exception e) {
                return ResponseEntity.badRequest().build();
            }
        }
        @DeleteMapping("/{id}")
        public ResponseEntity<String> deleteTopics(@PathVariable Topics id) {
            try {
                return ResponseEntity.ok(topicsService.deleteTopics(id.getId()));
            }
            catch (Exception e) {
                return ResponseEntity.notFound().build();
            }
        }
        @GetMapping("/extended/{id}")
        public List<Object[]> getTopicsByIdExtended(@PathVariable Integer id) {
            return topicsService.findByIdExtended(id);
        }
    }
