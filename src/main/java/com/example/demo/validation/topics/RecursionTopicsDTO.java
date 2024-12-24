package com.example.demo.validation.topics;

import com.example.demo.dto.TopicsDTO;
import com.example.demo.repository.TopicsRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RecursionTopicsDTO implements ConstraintValidator<RecursionValidation, TopicsDTO> {
    TopicsRepository topicsRepository;

    @Override
    public boolean isValid(TopicsDTO topicsDTO, ConstraintValidatorContext constraintValidatorContext) {

        if (topicsDTO.getParentId() == null) {
            return true;
        }
        Integer id = topicsDTO.getId();
        Integer parentId = topicsDTO.getParentId();
        if (id != null && id.equals(parentId)) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Parent id cannot be equals topic id")
                    .addPropertyNode("parentId").addConstraintViolation();
            return false;
        }
        if (!topicsRepository.existsById(topicsDTO.getParentId())) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Parent id does not exist")
                    .addPropertyNode("parentId").addConstraintViolation();
            return false;
        }
        return true;
    }
}
