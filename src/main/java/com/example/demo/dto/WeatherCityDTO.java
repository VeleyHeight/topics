package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class WeatherCityDTO {
    String name;
    Double lat;
    Double lon;
    String country;
    String state;
}
