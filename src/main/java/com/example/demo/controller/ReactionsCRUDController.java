package com.example.demo.controller;

import com.example.demo.dto.ReactionsDTO;
import com.example.demo.model.Questions;
import com.example.demo.model.Reactions;
import com.example.demo.model.Topics;
import com.example.demo.service.ReactionsService;
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
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/reactions")
public class ReactionsCRUDController {
    private final ReactionsService reactionsService;

    @GetMapping
    public ResponseEntity<List<ReactionsDTO>> getAllTopics() {
        return ResponseEntity.ok(reactionsService.findAll());
    }

    @PostMapping
    public ResponseEntity<ReactionsDTO> createReactions(@Valid @RequestBody ReactionsDTO reactionsDTO) {
        return ResponseEntity.ok(reactionsService.saveReactions(reactionsDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReactions(@PathVariable Integer id, @Valid @RequestBody ReactionsDTO reactionsDTO) {
        if (reactionsService.findById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reactions with this id is not exist");
        }
        reactionsDTO.setId(id);
        return ResponseEntity.ok(reactionsService.updateReactions(reactionsDTO));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchReactions(@PathVariable Integer id, @RequestBody HashMap<String, String> body) {
        if (reactionsService.findById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reactions with this id is not exist");
        }
        if (!body.containsKey("user_id") && !body.containsKey("type") && !body.containsKey("questionsId")) {
            return ResponseEntity.badRequest().body("Input is empty");
        }
        ReactionsDTO reactions;
        try {
            reactions = reactionsService.patchTopics(body, id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(reactions);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReactions(@PathVariable Integer id) {
        if (reactionsService.findById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reactions with this id is not exist");
        } else {
            return ResponseEntity.ok(reactionsService.deleteReactions(id));
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
