package com.example.demo.validation.questions;

import com.example.demo.repository.TopicsRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TopicIdForeignKey implements ConstraintValidator<ValidationQuestionsDTO, Integer> {
    private TopicsRepository topicsRepository;
    @Override
    public boolean isValid(Integer id, ConstraintValidatorContext constraintValidatorContext) {
        if (id == null){
            return false;
        }
        return topicsRepository.existsById(id);
    }
}
