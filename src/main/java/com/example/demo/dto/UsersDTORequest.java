package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;

public record UsersDTORequest(@NotBlank String username, @NotBlank String password) {
}
