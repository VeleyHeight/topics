package com.example.demo.dto.topicsDTO;

import com.example.demo.dto.validation.ValidationTopicsDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateTopicdDTOValidation {
    @Valid

    @NotBlank(message = "Title is blank")
    @Size(min = 5,max = 200,message = "Title size must be between 5 and 200")
    private String title;
    @NotBlank(message = "Title is blank")
    @Size(min = 5,max = 200,message = "Description size must be between 5 and 200")
    private String description;
    @ValidationTopicsDTO
    private String parentId;
}
