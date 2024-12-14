package com.example.demo.dto.validation.topics;

import com.example.demo.dto.TopicsDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class RecursionTopicsDTO implements ConstraintValidator<RecursionValidation, TopicsDTO> {


    @Override
    public boolean isValid(TopicsDTO topicsDTO, ConstraintValidatorContext constraintValidatorContext) {

        if (topicsDTO.getParentId() == null){
            return true;
        }
        Integer id = topicsDTO.getId();
        try {
            Integer parentId = topicsDTO.getParentId();
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
//        if(!topicsRepository.existsById(Integer.parseInt(topicsDTO.getParentId()))){
//            constraintValidatorContext.disableDefaultConstraintViolation();
//            constraintValidatorContext.buildConstraintViolationWithTemplate("Parent id does not exist")
//                    .addPropertyNode("parentId").addConstraintViolation();
//            return false;
//        }
        return true;
    }
}
