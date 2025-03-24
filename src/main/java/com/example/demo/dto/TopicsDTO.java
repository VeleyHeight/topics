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

public record TopicsDTO(
        @Valid
        @NotBlank(message = "Title is blank")
        @Size(min = 5, max = 200, message = "Title size must be between 5 and 200")
        String title,
        @NotBlank(message = "Description is blank")
        @Size(min = 5, max = 200, message = "Description size must be between 5 and 200")
        String description,
        Integer parentId,
        @Transient
        @CreationTimestamp
        Timestamp created_at,
        @Transient
        @UpdateTimestamp
        Timestamp updated_at
){}









