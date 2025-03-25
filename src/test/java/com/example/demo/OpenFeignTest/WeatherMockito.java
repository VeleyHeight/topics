package com.example.demo.OpenFeignTest;

import com.example.demo.dto.WeatherDTO;
import org.jetbrains.annotations.NotNull;

public class WeatherMockito {
    public static WeatherDTO getWeatherDTO() {
        return new WeatherDTO(new WeatherDTO.Weather[] {
                new WeatherDTO.Weather(
                        800,
                        "Clear",
                        "clear sky",
                        "01d"
                )
        },
                new WeatherDTO.Main(
                        11.6,
                        10.07,
                        11.6,
                        11.6,
                        1025,
                        48,
                        1025,
                        942
                ));
    }
}
