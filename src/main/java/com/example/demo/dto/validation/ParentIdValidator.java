package com.example.demo.dto.validation;

import com.example.demo.repository.TopicsRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ParentIdValidator implements ConstraintValidator<ValidationTopicsDTO,String> {
    private TopicsRepository topicsRepository;
    @Override
    public boolean isValid(String string, ConstraintValidatorContext constraintValidatorContext) {
        if (string == null){
            return true;
        }
        try{
            Integer id = Integer.parseInt(string);
            return topicsRepository.existsById(id);
        }
        catch (Exception e){
            return false;
        }
    }
}
