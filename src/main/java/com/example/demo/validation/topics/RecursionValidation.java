package com.example.demo.validation.topics;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
@Constraint(validatedBy = RecursionTopicsDTO.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RecursionValidation {
    String message() default "Invalid parent id";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
