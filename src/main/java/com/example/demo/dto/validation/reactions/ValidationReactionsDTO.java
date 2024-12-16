package com.example.demo.dto.validation.reactions;

import com.example.demo.dto.validation.questions.TopicIdForeignKey;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = QuestionsIdForeignKey.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidationReactionsDTO {
    String message() default "Invalid question id";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
