package com.example.demo.dto;

import com.example.demo.dto.validation.questions.ValidationQuestionsDTO;
import com.example.demo.dto.validation.reactions.ValidationReactionsDTO;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReactionsDTO {
    @Transient
    Integer id;
    @NotBlank
    @NotNull
    String user_id;
    @NotNull
    @NotBlank
    String type;
    @ValidationReactionsDTO
    Integer questionsId;
}
