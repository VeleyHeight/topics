package com.example.demo.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ReactionsDTO {
    @Transient
    Integer id;
    @NotBlank(message = "User is blank")
    String user_id;
    @NotBlank(message = "Type is required")
    @Size(min = 2, max = 50, message = "Type size must be between 2 and 50")
    String type;
    Integer questionsId;
}
