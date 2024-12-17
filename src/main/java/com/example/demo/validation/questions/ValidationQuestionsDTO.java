package com.example.demo.validation.questions;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = TopicIdForeignKey.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidationQuestionsDTO {
    String message() default "Invalid topic id";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
