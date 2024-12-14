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
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/questions")
public class QuestionsCRUDController {
    private final QuestionsService questionsService;
    @GetMapping
    public ResponseEntity<Page<Questions>> getAllQuestions(@RequestParam(required = false) String questions,
                                                           @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size){
        if (questions != null && !questions.isEmpty()){
            return ResponseEntity.ok(questionsService.findAllByQuestionContainingIgnoreCase(questions, PageRequest.of(page, size)));
        }
        else {
            return ResponseEntity.ok(questionsService.findAll(PageRequest.of(page,size)));
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getQuestionsById(@PathVariable Integer id){
        if (questionsService.findById(id) == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Question with this id is not exist");
        }
        else {
            return ResponseEntity.ok(questionsService.findById(id));
        }
    }
    @PostMapping
    public ResponseEntity<Questions> createQuestions(@Valid @RequestBody QuestionsDTO questionsDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(questionsService.saveQuestions(questionsDTO));
    }
    // С этого момента добавить оставшуюся валидацию
    @PutMapping("/{id}")
    public ResponseEntity<Questions> updateQuestions(@PathVariable Integer id, @RequestBody Questions questions){
        Questions questionsFind = questionsService.findById(id);
        if (questionsFind != null){
            questionsFind.setQuestion(questions.getQuestion());
            questionsFind.setAnswer(questions.getAnswer());
            questionsFind.set_popular(questions.is_popular());
            return ResponseEntity.ok(questionsService.updateQuestions(questions));
        }
        return ResponseEntity.notFound().build();
    }
    @PatchMapping("/{id}")
    public ResponseEntity<Questions> patchQuestions(@PathVariable Integer id, @RequestBody Map<String, Object> body){
        Questions questions = questionsService.findById(id);
        if (questions != null){
            body.forEach((key, value) ->{
                    switch (key) {
                        case "questions":
                            questions.setQuestion((String) value);
                            break;
                        case "answer":
                            questions.setAnswer((String) value);
                            break;
                        case "topicId":
                            questions.setTopicId((Topics) value);
                            break;
                        case "is_popular":
                            questions.set_popular((boolean) value);
                            break;
                        default:
                            throw new IllegalArgumentException("Invalid field: " + key);
                    }
            });
            return ResponseEntity.ok(questionsService.updateQuestions(questions));
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteQuestions(@PathVariable Integer id){
        try {
            return ResponseEntity.ok(questionsService.deleteQuestions(id));
        }
        catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("extended/{id}")
    public ResponseEntity<ExtendedTopicsDTO> getQuestionsByIdExtended(@PathVariable Integer id){
        return ResponseEntity.ok(questionsService.findByIdExtended(id));
    }
}
