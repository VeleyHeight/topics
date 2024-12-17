package com.example.demo.validation.topics;

import com.example.demo.repository.TopicsRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ParentIdValidator implements ConstraintValidator<ValidationTopicsDTO,Integer> {
    private TopicsRepository topicsRepository;
    @Override
    public boolean isValid(Integer id, ConstraintValidatorContext constraintValidatorContext) {
        if (id == null){
            return true;
        }
            return topicsRepository.existsById(id);
    }
}
