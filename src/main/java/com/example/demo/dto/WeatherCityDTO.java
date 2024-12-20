package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@AllArgsConstructor
public class WeatherCityDTO {

    String name;
    Double lat;
    Double lon;
    String country;
    String state;
}
