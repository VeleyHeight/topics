package com.example.demo.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

//todo @Data избыточна, используй отдельные аннотации, измени классы на record и добавь валидацию полей при создании dto!!!

public record WeatherCityDTO(
        @Valid
        @NotBlank
        String name,
        @NotNull
        Double lat,
        @NotNull
        Double lon,
        @NotBlank
        String country,
        @NotBlank
        String state) {
}

