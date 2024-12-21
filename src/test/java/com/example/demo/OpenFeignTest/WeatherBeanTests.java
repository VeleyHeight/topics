package com.example.demo.OpenFeignTest;

import com.example.demo.client.GetCityWeather;
import com.example.demo.client.GetWeather;
import com.example.demo.controller.TopicsCRUDController;
import com.example.demo.dto.WeatherCityDTO;
import com.example.demo.dto.WeatherDTO;
import com.example.demo.service.TopicsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.List;

@AutoConfigureMockMvc
@SpringBootTest
class WeatherBeanTests {
    @Autowired
    private GetCityWeather getCityWeather;
    @Autowired
    private GetWeather getWeather;
    @Autowired
    private TopicsService topicsService;
    @Autowired
    private TopicsCRUDController topicsCRUDController;
    @Autowired
    private MockMvc mockMvc;
    @Test
    @DisplayName("Тестирование контроллера для города")
    public void controllerCorrect() throws Exception {
        String city = "Ярославль";
        mockMvc.perform(MockMvcRequestBuilders.get("/topics/weather")
                        .contentType("application/json").param("city",city))
                .andExpect(status().isOk()).andExpect(jsonPath("$.Weather").exists())
                .andExpect(jsonPath("$.Description").exists())
                .andExpect(jsonPath("$.Temp").isNumber())
                .andExpect(jsonPath("$.Pressure").isNumber());
    }
    @Test
    @DisplayName("Тестирование валидации контроллера для null параметра")
    public void controllerValidation() throws Exception {
        String city = null;
        mockMvc.perform(MockMvcRequestBuilders.get("/topics/weather")
                        .contentType("application/json").param("city",city))
                .andExpect(status().is4xxClientError());
    }
    @Test
    @DisplayName("Тестирование валидации контроллера для несуществующего города")
    public void controllerUnknownCity() throws Exception {
        String city = "Неизвестный";
        mockMvc.perform(MockMvcRequestBuilders.get("/topics/weather")
                        .contentType("application/json").param("city",city))
                .andExpect(status().is4xxClientError());
    }
    @Test
    @DisplayName("Получение погоды города")
    public void getWeatherAndGeo() {
        String city = "Ярославль";
        List<WeatherCityDTO> weatherCityDTO = getCityWeather.getGeoByCity(city,1,GetCityWeather.api);
        Assertions.assertNotNull(weatherCityDTO);
        Assertions.assertTrue(!weatherCityDTO.isEmpty());
        Assertions.assertNotNull(weatherCityDTO.get(0).getLat());
        Assertions.assertNotNull(weatherCityDTO.get(0).getLon());
        WeatherDTO weatherDTO = getWeather.getWeather(weatherCityDTO.get(0).getLat(),weatherCityDTO.get(0).getLon(),GetWeather.api,"metric");
        Assertions.assertNotNull(weatherDTO);
        Assertions.assertNotNull(weatherDTO.getMain());
        Assertions.assertTrue(weatherDTO.getWeather().length > 0);
        System.out.println(weatherDTO);
    }
    @Test
    @DisplayName("Получение ошибки для неизвестного города")
    public void unknownCity(){
        String city = "Неизвестный";
        List<WeatherCityDTO> weatherCityDTO = getCityWeather.getGeoByCity(city,1,GetCityWeather.api);
        Assertions.assertTrue(weatherCityDTO.isEmpty());
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->getWeather.getWeather(weatherCityDTO.get(0).getLat(),weatherCityDTO.get(0).getLon(),GetWeather.api,"metric"));
    }
    @Test
    @DisplayName("Тестирование сервиса для корректного города")
    public void serviceCorrect(){
        String city = "Ярославль";
        ResponseEntity<?> response = topicsService.getWeatherInCity(city);
        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        System.out.println(topicsService.getWeatherInCity(city));
    }
    @Test
    @DisplayName("Тестирование сервиса для не корректного города")
    public void serviceIncorrect(){
        String city = "Неизвестный";
        ResponseEntity<?> response = topicsService.getWeatherInCity(city);
        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.getStatusCode().is4xxClientError());
        System.out.println(topicsService.getWeatherInCity(city));
    }
    @Test
    @DisplayName("Тестирование контроллера для не существующего города")
    public void controllerIncorrect(){

        String city = "Неизвестный";
        ResponseEntity<?> response = topicsCRUDController.getWeatherInCity(city);
        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.getStatusCode().is4xxClientError());
        System.out.println(topicsService.getWeatherInCity(city));
    }
}
