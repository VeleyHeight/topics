package com.example.demo.controller;

import com.example.demo.dto.ReactionsDTO;
import com.example.demo.filter.ReactionsFilter;
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
    private ReactionsService reactionsService;

    @GetMapping
    public ResponseEntity<List<ReactionsDTO>> getAllTopics() {
        return ResponseEntity.ok(reactionsService.findAll());
    }

    @PostMapping
    public ResponseEntity<ReactionsDTO> createReactions(@Valid @RequestBody ReactionsDTO reactionsDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reactionsService.saveReactions(reactionsDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReactions(@PathVariable Integer id, @Valid @RequestBody ReactionsDTO reactionsDTO) {
        return ResponseEntity.ok(reactionsService.updateReactions(id, reactionsDTO));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchReactions(@PathVariable Integer id, @RequestBody ReactionsFilter filter) {
        return ResponseEntity.status(HttpStatus.OK).body(reactionsService.patchTopics(filter, id));
    }

    @DeleteMapping("/{id}")
    public void deleteReactions(@PathVariable Integer id) {
        reactionsService.deleteReactions(id);
    }

}
