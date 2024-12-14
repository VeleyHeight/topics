package com.example.demo.dto;

import com.example.demo.dto.validation.questions.ValidationQuestionsDTO;
import jakarta.persistence.Transient;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class QuestionsDTO {
    @Valid
            @NotNull(message = "Question is required")
            @NotBlank(message = "Question is blank")
    @Size(max = 1000,message = "Question size must be between 5 and 10000")
    String question;
    @NotNull(message = "Answer is required")
    @NotBlank(message = "Answer is blank")
    @Size(max = 10000,message = "Answer size must be between 5 and 10000")
    String answer;
    boolean is_popular;
    @NotNull
    @ValidationQuestionsDTO
    Integer topicId;
    @Transient
    private Integer id;
}
