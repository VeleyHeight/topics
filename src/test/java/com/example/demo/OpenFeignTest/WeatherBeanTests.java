package com.example.demo.OpenFeignTest;

import com.example.demo.client.GetCityWeather;
import com.example.demo.client.GetWeather;
import com.example.demo.controller.TopicsCRUDController;
import com.example.demo.dto.WeatherCityDTO;
import com.example.demo.dto.WeatherDTO;
import com.example.demo.service.TopicsService;
import org.junit.jupiter.api.*;
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
    @Nested
    @DisplayName("Тестирование контроллера")
    public class controllerTested {
        @Test
        @DisplayName("Существующий город")
        public void controllerCorrect() throws Exception {
            String city = "Ярославль";
            mockMvc.perform(MockMvcRequestBuilders.get("/topics/weather")
                            .contentType("application/json").param("city", city))
                    .andExpect(status().isOk()).andExpect(jsonPath("$.Weather").exists())
                    .andExpect(jsonPath("$.Description").exists())
                    .andExpect(jsonPath("$.Temp").isNumber())
                    .andExpect(jsonPath("$.Pressure").isNumber());
        }

        @Test
        @DisplayName("Валидация для null параметра")
        public void controllerValidation() throws Exception {
            String city = null;
            mockMvc.perform(MockMvcRequestBuilders.get("/topics/weather")
                            .contentType("application/json").param("city", city))
                    .andExpect(status().is4xxClientError());
        }

        @Test
        @DisplayName("Несуществующий город")
        public void controllerUnknownCity() throws Exception {
            String city = "Неизвестный";
            mockMvc.perform(MockMvcRequestBuilders.get("/topics/weather")
                            .contentType("application/json").param("city", city))
                    .andExpect(status().is4xxClientError());
        }
    }
    @Nested
    @DisplayName("Тестирование тонких клиентов")
    public class clientTested {
        @Test
        @DisplayName("Получение погоды для существующего города")
        public void getWeatherAndGeo() {
            String city = "Ярославль";
            List<WeatherCityDTO> weatherCityDTO = getCityWeather.getGeoByCity(city,1,GetCityWeather.api);
            Assertions.assertNotNull(weatherCityDTO);
            Assertions.assertTrue(!weatherCityDTO.isEmpty());
            Assertions.assertNotNull(weatherCityDTO.get(0).getLat());
            Assertions.assertNotNull(weatherCityDTO.get(0).getLon());
            System.out.println(weatherCityDTO.get(0));
            WeatherDTO weatherDTO = getWeather.getWeather(weatherCityDTO.get(0).getLat(),weatherCityDTO.get(0).getLon(),GetWeather.api,"metric");
            Assertions.assertNotNull(weatherDTO);
            Assertions.assertNotNull(weatherDTO.getMain());
            Assertions.assertTrue(weatherDTO.getWeather().length > 0);
            System.out.println(weatherDTO);
        }
        @Test
        @DisplayName("Получение погоды по координатам")
        public void getWeather(){
            WeatherDTO weatherDTO = getWeather.getWeather(55.7504461,37.6174943,GetWeather.api,"metric");
            Assertions.assertNotNull(weatherDTO);
            System.out.println(weatherDTO);
        }
        @Test
        @DisplayName("Получение координат по названию города")
        public void getCity(){
            String city = "Ярославль";
            List<WeatherCityDTO> weatherCityDTO = getCityWeather.getGeoByCity(city,1,GetCityWeather.api);
            Assertions.assertTrue(!weatherCityDTO.isEmpty());
            Assertions.assertNotNull(weatherCityDTO.get(0).getLat());
            Assertions.assertNotNull(weatherCityDTO.get(0).getLon());
            System.out.println(weatherCityDTO.get(0));
        }
    }
    @Nested
    @DisplayName("Тестирование сервиса по получению погоды для заданного города")
    public class serviceTests {
        @Test
        @DisplayName("Существующий город")
        public void serviceCorrect() {
            String city = "Ярославль";
            ResponseEntity<?> response = topicsService.getWeatherInCity(city);
            Assertions.assertNotNull(response);
            Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
            System.out.println(topicsService.getWeatherInCity(city));
        }
        @Test
        @DisplayName("Не существующий город")
        public void serviceIncorrect() {
            String city = "Неизвестный";
            ResponseEntity<?> response = topicsService.getWeatherInCity(city);
            Assertions.assertNotNull(response);
            Assertions.assertTrue(response.getStatusCode().is4xxClientError());
            System.out.println(topicsService.getWeatherInCity(city));
        }
    }
}
