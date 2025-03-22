package com.example.demo.controller;

import com.example.demo.dto.extended.ExtendedTopicsDTO;
import com.example.demo.dto.QuestionsDTO;
import com.example.demo.model.Questions;
import com.example.demo.model.Topics;
import com.example.demo.service.QuestionsService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    private QuestionsService questionsService;

    @GetMapping
    public ResponseEntity<Page<QuestionsDTO>> getAllQuestions(@RequestParam(required = false) String questions,
                                                              @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size) {
        if (questions != null && !questions.isEmpty()) {
            return ResponseEntity.ok(questionsService.findAllByQuestionContainingIgnoreCase(questions, PageRequest.of(page, size)));
        } else {
            return ResponseEntity.ok(questionsService.findAll(PageRequest.of(page, size)));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getQuestionsById(@PathVariable Integer id) {
        if (questionsService.findById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Question with this id is not exist");
        } else {
            return ResponseEntity.ok(questionsService.findById(id));
        }
    }

    @PostMapping
    public ResponseEntity<QuestionsDTO> createQuestions(@Valid @RequestBody QuestionsDTO questionsDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(questionsService.saveQuestions(questionsDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateQuestions(@PathVariable Integer id, @Valid @RequestBody QuestionsDTO questionsDTO) {
        if (questionsService.findById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Question with this id is not exist");
        }
//        questionsDTO.setId(id); //todo ????
        return ResponseEntity.ok(questionsService.updateQuestions(questionsDTO));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchQuestions(@PathVariable Integer id, @RequestBody HashMap<String, String> body) {
        if (questionsService.findById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Question with this id is not exist");
        }
        if (!body.containsKey("questions") && !body.containsKey("answer") && !body.containsKey("topicId") && !body.containsKey("is_popular")) {
            return ResponseEntity.badRequest().body("Input is empty");
        }
        QuestionsDTO questions;
        try {
            questions = questionsService.patchTopics(body, id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(questions);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuestions(@PathVariable Integer id) {
        if (questionsService.findById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Question with this id does not exist");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(questionsService.deleteQuestions(id));
        }
    }

    @GetMapping("extended/{id}")
    public ResponseEntity<?> getQuestionsByIdExtended(@PathVariable Integer id) {
        if (questionsService.findById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Question with this id does not exist");
        } else {
            return ResponseEntity.ok(questionsService.findByIdExtended(id));
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
