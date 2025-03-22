package com.example.demo.dto;

import jakarta.persistence.Transient;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Data
public class QuestionsDTO {
    @Valid

    @Transient
    private Integer id;
    @NotBlank(message = "Question is blank")
    @Size(min = 5, max = 1000, message = "Question size must be between 5 and 1000")
    String question;
    @NotBlank(message = "Answer is blank")
    @Size(min = 5, max = 10000, message = "Answer size must be between 5 and 10000")
    String answer;
    boolean is_popular;
    @NotNull
    Integer topicId;
    @Transient
    @CreationTimestamp
    private Timestamp created_at;
    @Transient
    @UpdateTimestamp
    private Timestamp updated_at;

}
