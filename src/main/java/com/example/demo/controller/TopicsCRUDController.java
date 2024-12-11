package com.example.demo.controller;

import com.example.demo.dto.extended.ExtendedTopicsDTO;
import com.example.demo.dto.topicsDTO.TopicsDTO;
import com.example.demo.dto.topicsDTO.TopicsDTOValidation;
import com.example.demo.model.Topics;
import com.example.demo.repository.TopicsRepository;
import com.example.demo.service.QuestionsService;
import com.example.demo.service.TopicsService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@RestController
    @AllArgsConstructor
    @RequestMapping("/topics")
    public class TopicsCRUDController {
        private final TopicsService topicsService;
        private final QuestionsService questionsService;
        private final TopicsRepository topicsRepository;

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
        public ResponseEntity<?> createTopics(@Valid @RequestBody TopicsDTOValidation topicsDTOValidation, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
                if (bindingResult.hasErrors()){
                    Map<String, String> errors = new HashMap<>();
                    bindingResult.getAllErrors().forEach((error)->{
                        String field = error.getObjectName();
                        String message = error.getDefaultMessage();
                        errors.put(field,message);
                    });
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
                }
                return ResponseEntity.ok(topicsService.saveTopics(topicsDTOValidation));

        }
        @PutMapping("/{id}")
        public ResponseEntity<Topics> updateTopics(@PathVariable Integer id, @RequestBody Topics topics) {
            Topics topicsFind = topicsService.findById(id);
            if (topicsFind != null){
                if (topics.getParentId().toString().equals(topicsFind.getId().toString())){
                    return ResponseEntity.badRequest().build();
                }
                topicsFind.setTitle(topics.getTitle());
                topicsFind.setDescription(topics.getDescription());
                topicsFind.setParentId(topics.getParentId());
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
                            topics.setParentId((Topics) value);
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
        public ExtendedTopicsDTO getTopicsByIdExtended(@PathVariable Integer id) {

            return topicsService.findByIdExtended(id);
        }
        @GetMapping("/extended/test/{id}")
        public ResponseEntity<ExtendedTopicsDTO> getTopicsByIdExtended1(@PathVariable Integer id) {

            return ResponseEntity.notFound().build();
        }
    }
