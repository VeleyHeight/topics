package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

//todo @Data избыточна, используй отдельные аннотации, измени классы на record и добавь валидацию полей при создании dto!!!
@Data
@AllArgsConstructor
public class WeatherCityDTO {

    String name;
    Double lat;
    Double lon;
    String country;
    String state;
}
