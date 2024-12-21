package com.example.demo.OpenFeignTest;

import com.example.demo.client.GetCityWeather;
import com.example.demo.client.GetWeather;
import com.example.demo.dto.WeatherCityDTO;
import com.example.demo.dto.WeatherDTO;
import com.example.demo.impl.TopicsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.OpenFeignTest.WeatherMockito.getWeatherDTO;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;

@WebMvcTest
@ExtendWith(MockitoExtension.class)
class WeatherMockTests {
    @Mock
    private GetWeather getWeather;
    @Mock
    private GetCityWeather getCityWeather;
    @InjectMocks
    private TopicsServiceImpl topicsService;

    MockMvc mockMvc;
    @Test
    @DisplayName("Получение погоды города")
    public void testWeather() {
        String city = "Москва";
        List<WeatherCityDTO> weatherCityDTO = new ArrayList<>();
        weatherCityDTO.add(new WeatherCityDTO("Moscow",55.7504461,37.6174943, "RU", "Moscow"));
        WeatherDTO weatherDTO = getWeatherDTO();
        Mockito.when(getCityWeather.getGeoByCity(city,1,GetCityWeather.api)).thenReturn(weatherCityDTO);
        Mockito.when(getWeather.getWeather(weatherCityDTO.get(0).getLat(),weatherCityDTO.get(0).getLon(),GetWeather.api,"metric")).thenReturn(weatherDTO);
        ResponseEntity<?> response = topicsService.getWeatherInCity(city);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    @DisplayName("D")
    public void controllerTopicsWeather(){

    }
}
