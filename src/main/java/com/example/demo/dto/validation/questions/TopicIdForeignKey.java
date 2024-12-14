package com.example.demo.dto.validation.questions;

import com.example.demo.repository.QuestionsRepository;
import com.example.demo.repository.TopicsRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TopicIdForeignKey implements ConstraintValidator<ValidationQuestionsDTO, Integer> {
    private QuestionsRepository questionsRepository;
    @Override
    public boolean isValid(Integer id, ConstraintValidatorContext constraintValidatorContext) {
        if (id == null){
            return false;
        }
        return questionsRepository.existsById(id);
    }
}
