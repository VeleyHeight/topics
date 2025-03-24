package com.example.demo.dto;

import jakarta.persistence.Transient;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

public record QuestionsDTO(
        @Valid
        @NotBlank(message = "Question is blank")
        @Size(min = 5, max = 1000, message = "Question size must be between 5 and 1000")
        String question,
        @Size(min = 5, max = 10000, message = "Answer size must be between 5 and 10000")
        String answer,
        Boolean is_popular,
        @NotNull
        Integer topicId,
        @Transient
        @CreationTimestamp
        Timestamp created_at,
        @Transient
        @UpdateTimestamp
        Timestamp updated_at
) {
}
