package com.example.demo.dto;

import lombok.Data;

//todo @Data избыточна, используй отдельные аннотации, измени классы на record и добавь валидацию полей при создании dto!!!
@Data
public class WeatherDTO {
    private Weather[] weather;
    private Main main;

    @Data
    public static class Weather {
        private int id;
        private String main;
        private String description;
        private String icon;
    }

    @Data
    public static class Main {
        private double temp;
        private double feels_like;
        private double temp_min;
        private double temp_max;
        private int pressure;
        private int humidity;
        private int sea_level;
        private int grnd_level;
    }
}
