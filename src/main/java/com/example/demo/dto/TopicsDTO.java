package com.example.demo.dto;

import com.example.demo.validation.topics.ValidationTopicsDTO;
import jakarta.persistence.Transient;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

//@RecursionValidation
//@Validated
@Data
public class TopicsDTO {
    @Valid

    @Transient
    private Integer id;
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
    @CreationTimestamp
    private Timestamp created_at;
    @Transient
    @UpdateTimestamp
    private Timestamp updated_at;
}
