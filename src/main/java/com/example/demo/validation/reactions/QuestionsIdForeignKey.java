package com.example.demo.validation.reactions;

import com.example.demo.repository.QuestionsRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class QuestionsIdForeignKey implements ConstraintValidator<ValidationReactionsDTO, Integer> {
    QuestionsRepository repository;

    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
        if (integer == null) {
            return false;
        }
        return repository.existsById(integer);
    }
}
