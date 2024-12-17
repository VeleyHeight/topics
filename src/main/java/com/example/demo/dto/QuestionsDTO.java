package com.example.demo.dto;

import com.example.demo.validation.questions.ValidationQuestionsDTO;
import jakarta.persistence.Transient;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Data
public class QuestionsDTO {
    @Valid
    @Transient
    private Integer id;
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
    @CreationTimestamp
    private Timestamp created_at;
    @Transient
    @UpdateTimestamp
    private Timestamp updated_at;

}
