package com.example.demo.dto;

import com.example.demo.dto.validation.topics.ValidationTopicsDTO;
import jakarta.persistence.Transient;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

//@RecursionValidation
@Data
public class TopicsDTO {
    @Valid

    @NotNull(message = "Title is required")
    @NotBlank(message = "Title is blank")
    @Size(min = 5,max = 200,message = "Title size must be between 5 and 200")
    private String title;
    @NotNull(message = "Description is required")
    @NotBlank(message = "Description is blank")
    @Size(min = 5,max = 200,message = "Description size must be between 5 and 200")
    private String description;
    @ValidationTopicsDTO
    private Integer parentId;
    @Transient
    private Integer id;
}
