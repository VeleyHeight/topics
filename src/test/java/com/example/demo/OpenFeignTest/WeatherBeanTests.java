package com.example.demo.OpenFeignTest;

import com.example.demo.client.GetCityWeather;
import com.example.demo.client.GetWeather;
import com.example.demo.dto.WeatherCityDTO;
import com.example.demo.dto.WeatherDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class WeatherBeanTests {
    @Autowired
    private GetCityWeather getCityWeather;
    @Autowired
    private GetWeather getWeather;
    @Test
    @DisplayName("Получение погоды города")
    public void testWeather() {
        String city = "Ярославль";
        List<WeatherCityDTO> weatherCityDTO = getCityWeather.getGeoByCity(city,1,GetCityWeather.api);
        Assertions.assertNotNull(weatherCityDTO);
        WeatherDTO weatherDTO = getWeather.getWeather(weatherCityDTO.get(0).getLat(),weatherCityDTO.get(0).getLon(),GetWeather.api,"metric");
        Assertions.assertNotNull(weatherDTO);
    }
}
