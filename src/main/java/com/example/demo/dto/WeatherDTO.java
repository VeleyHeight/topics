package com.example.demo.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Value;

//todo @Data избыточна, используй отдельные аннотации, измени классы на record и добавь валидацию полей при создании dto!!!

public record WeatherDTO(@Valid
                         @NotNull
                         @Size(min = 1, message = "Должна быть погода для данного места")
                         Weather[] weather,
                         @NotNull Main main
) {
    public record Weather(
            int id,
            @NotBlank
            String main,
            @NotBlank
            String description,
            @NotBlank
            String icon
    ) {
    }

    public record Main(
            double temp,
            double feels_like,
            double temp_min,
            double temp_max,
            int pressure,
            int humidity,
            int sea_level,
            int grnd_level
    ) {
    }
}

