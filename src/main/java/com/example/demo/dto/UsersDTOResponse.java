package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;

import java.sql.Timestamp;

public record UsersDTOResponse(Integer id, @NotBlank String username, Timestamp createdAt) {
}
