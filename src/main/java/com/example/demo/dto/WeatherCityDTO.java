package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

//todo @Data избыточна, используй отдельные аннотации, измени классы на record и добавь валидацию полей при создании dto!!!

public record WeatherCityDTO (
    String name,
    Double lat,
    Double lon,
    String country,
    String state){}

