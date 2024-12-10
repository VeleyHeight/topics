package com.example.demo.controller;

import com.example.demo.dto.ReactionsDTO;
import com.example.demo.model.Questions;
import com.example.demo.model.Reactions;
import com.example.demo.model.Topics;
import com.example.demo.service.ReactionsService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/reactions")
public class ReactionsCRUDController {
    private final ReactionsService reactionsService;
    @GetMapping
    public ResponseEntity<List<Reactions>> getAllTopics() {
        return ResponseEntity.ok(reactionsService.findAll());
    }
    @PostMapping
    public ResponseEntity<Reactions> createReactions(@RequestBody ReactionsDTO reactionsDTO) {
        try {
            return ResponseEntity.ok(reactionsService.saveReactions(reactionsDTO));
        }
        catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Reactions> updateReactions(@RequestBody Reactions reactions) {
        return ResponseEntity.ok(reactionsService.updateReactions(reactions));
    }
    @PatchMapping("/{id}")
    public ResponseEntity<Reactions> patchReactions(@PathVariable Integer id, @RequestBody Map<String,Object> body) {
        if (reactionsService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        try{
            Reactions reactions = reactionsService.findById(id);
            body.forEach((key, value) -> {
                switch (key) {
                    case "user_id":
                        reactions.setUser_id((UUID)value);
                        break;
                    case "type":
                        reactions.setType((String)value);
                        break;
                    case "questions_id":
                        reactions.setQuestionsId((Questions) value);
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid key: " + key);
                }
            });
            return ResponseEntity.ok(reactionsService.updateReactions(reactions));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReactions(@RequestParam Integer id,@RequestBody Reactions reactions) {
        if (reactionsService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        else {
            return ResponseEntity.ok(reactionsService.deleteReactions(id));
        }
    }
}
