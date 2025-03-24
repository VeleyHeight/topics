package com.example.demo.controller;

import com.example.demo.dto.extended.ExtendedTopicsDTO;
import com.example.demo.dto.QuestionsDTO;
import com.example.demo.filter.QuestionsFilter;
import com.example.demo.model.Questions;
import com.example.demo.model.Topics;
import com.example.demo.service.QuestionsService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/questions")
public class QuestionsCRUDController {
    private final QuestionsService questionsService;

    @GetMapping
    public ResponseEntity<PagedModel<QuestionsDTO>> getAllQuestions(@ModelAttribute @ParameterObject QuestionsFilter questionsFilter, @PageableDefault @ParameterObject Pageable pageable) {
        return ResponseEntity.ok(new PagedModel<>(questionsService.findAll(questionsFilter, pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getQuestionsById(@PathVariable Integer id) {
        return ResponseEntity.ok(questionsService.findById(id));
    }

    @PostMapping
    public ResponseEntity<QuestionsDTO> createQuestions(@Valid @RequestBody QuestionsDTO questionsDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(questionsService.saveQuestions(questionsDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateQuestions(@PathVariable Integer id, @Valid @RequestBody QuestionsDTO questionsDTO) {
        return ResponseEntity.ok(questionsService.updateQuestions(id, questionsDTO));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchQuestions(@PathVariable Integer id, @RequestBody QuestionsFilter filter) {
        return ResponseEntity.ok(questionsService.patchTopics(filter, id));
    }

    @DeleteMapping("/{id}")
    public void deleteQuestions(@PathVariable Integer id) {
        questionsService.deleteQuestions(id);
    }

    @GetMapping("extended/{id}")
    public ResponseEntity<?> getQuestionsByIdExtended(@PathVariable Integer id) {
        return ResponseEntity.ok(questionsService.findByIdExtended(id));
    }

}
