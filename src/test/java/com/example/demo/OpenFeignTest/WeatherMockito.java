package com.example.demo.OpenFeignTest;

import com.example.demo.dto.WeatherDTO;
import org.jetbrains.annotations.NotNull;

public class WeatherMockito {
    public static @NotNull WeatherDTO getWeatherDTO() {
        WeatherDTO weatherDTO = new WeatherDTO();
        WeatherDTO.Weather weather = new WeatherDTO.Weather();
        weather.setId(800);
        weather.setMain("Clear");
        weather.setDescription("clear sky");
        weather.setIcon("01d");
        weatherDTO.setWeather(new WeatherDTO.Weather[]{weather});
        WeatherDTO.Main main = new WeatherDTO.Main();
        main.setTemp(11.6);
        main.setFeels_like(10.07);
        main.setTemp_min(11.6);
        main.setTemp_max(11.6);
        main.setPressure(1025);
        main.setHumidity(48);
        main.setSea_level(1025);
        main.setGrnd_level(942);
        weatherDTO.setMain(main);
        return weatherDTO;
    }
}
