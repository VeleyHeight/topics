package com.example.demo.dto.validation;

import com.example.demo.dto.topicsDTO.TopicsDTOValidation;
import com.example.demo.repository.TopicsRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class RecursionTopicsDTO implements ConstraintValidator<RecursionValidation, TopicsDTOValidation> {
    @Override
    public boolean isValid(TopicsDTOValidation topicsDTOValidation, ConstraintValidatorContext constraintValidatorContext) {

        if (topicsDTOValidation.getParentId() == null){
            return true;
        }
        Integer id = topicsDTOValidation.getId();
        try {
            Integer parentId = Integer.parseInt(topicsDTOValidation.getParentId());
            if (id != null && id.equals(parentId)) {
                constraintValidatorContext.disableDefaultConstraintViolation();
                constraintValidatorContext.buildConstraintViolationWithTemplate("Parent id cannot be equals topic id")
                        .addPropertyNode("parentId").addConstraintViolation();
                return false;
            }

        }
        catch (Exception e){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Parent id is not Integer")
                    .addPropertyNode("parentId").addConstraintViolation();
            return false;
        }
//        if(!topicsRepository.existsById(Integer.parseInt(topicsDTOValidation.getParentId()))){
//            constraintValidatorContext.disableDefaultConstraintViolation();
//            constraintValidatorContext.buildConstraintViolationWithTemplate("Parent id does not exist")
//                    .addPropertyNode("parentId").addConstraintViolation();
//            return false;
//        }
        return true;
    }
}
