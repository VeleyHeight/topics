package com.example.demo.OpenFeignTest;

import com.example.demo.client.GetCityWeather;
import com.example.demo.client.GetWeather;
import com.example.demo.controller.TopicsCRUDController;
import com.example.demo.dto.WeatherCityDTO;
import com.example.demo.dto.WeatherDTO;
import com.example.demo.impl.TopicsServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.demo.OpenFeignTest.WeatherMockito.getWeatherDTO;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class WeatherMockTests {
    @Mock
    private GetWeather getWeather;
    @Mock
    private GetCityWeather getCityWeather;
    @InjectMocks
    private TopicsServiceImpl topicsService;
    @Mock
    private TopicsServiceImpl topicsServiceMock;
    @InjectMocks
    private TopicsCRUDController topicsCRUDController;
    MockMvc mockMvc;
    String city;
    List<WeatherCityDTO> weatherCityDTO;
    WeatherDTO weatherDTO;

    @BeforeEach
    void setUp() {
        city = "Москва";
        weatherCityDTO = new ArrayList<>();
        weatherCityDTO.add(new WeatherCityDTO("Moscow", 55.7504461, 37.6174943, "RU", "Moscow"));
        weatherDTO = getWeatherDTO();
        mockMvc = MockMvcBuilders.standaloneSetup(topicsCRUDController).build();
    }

    @Nested
    @DisplayName("Тестирование контроллера")
    public class controllerTested {
        @Test
        @DisplayName("Существующий город")
        public void controllerCorrect() throws Exception {
            HashMap<String, Object> response = new HashMap<>();
            response.put("Weather", weatherDTO.getWeather()[0].getMain());
            response.put("Description", weatherDTO.getWeather()[0].getDescription());
            response.put("Temp", weatherDTO.getMain().getTemp());
            response.put("Pressure", weatherDTO.getMain().getPressure());
            ResponseEntity responseEntity = ResponseEntity.ok(response);
            Mockito.when(topicsServiceMock.getWeatherInCity(city)).thenReturn(responseEntity);
            mockMvc.perform(MockMvcRequestBuilders.get("/topics/weather")
                            .contentType("application/json").param("city", city))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.Weather").value("Clear"))
                    .andExpect(jsonPath("$.Description").value("clear sky"))
                    .andExpect(jsonPath("$.Temp").value("11.6"))
                    .andExpect(jsonPath("$.Pressure").value(1025));
            System.out.println(response);
        }

        @Test
        @DisplayName("Валидация для null параметра")
        public void controllerValidation() throws Exception {
            city = null;
            ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            Mockito.lenient().when(topicsServiceMock.getWeatherInCity(city)).thenReturn(responseEntity);
            mockMvc.perform(MockMvcRequestBuilders.get("/topics/weather")
                            .contentType("application/json").param("city", city))
                    .andExpect(status().is4xxClientError());
            System.out.println(responseEntity);
        }

        @Test
        @DisplayName("Несуществующий город")
        public void controllerUnknownCity() throws Exception {
            city = "Неизвестный";
            ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).body("City not found");
            ;
            Mockito.lenient().when(topicsServiceMock.getWeatherInCity(city)).thenReturn(responseEntity);
            mockMvc.perform(MockMvcRequestBuilders.get("/topics/weather")
                            .contentType("application/json").param("city", city))
                    .andExpect(status().is4xxClientError());
            System.out.println(responseEntity);
        }
    }

    @Nested
    @DisplayName("Тестирование тонких клиентов")
    public class clientTested {
        @Test
        @DisplayName("Получение погоды для существующего города")
        public void getWeatherAndGeo() {
            Mockito.when(getCityWeather.getGeoByCity(city, 1, GetCityWeather.api)).thenReturn(weatherCityDTO);
            Mockito.when(getWeather.getWeather(weatherCityDTO.get(0).getLat(), weatherCityDTO.get(0).getLon(), GetWeather.api, "metric")).thenReturn(weatherDTO);
            List<WeatherCityDTO> weatherCityDTOMock = getCityWeather.getGeoByCity(city, 1, GetCityWeather.api);
            Assertions.assertEquals(1, weatherCityDTOMock.size());
            Assertions.assertNotNull(weatherCityDTOMock.get(0).getLat());
            Assertions.assertNotNull(weatherCityDTOMock.get(0).getLon());
            System.out.println(weatherCityDTO.get(0));
            WeatherDTO weatherDTOMock = getWeather.getWeather(weatherCityDTO.get(0).getLat(), weatherCityDTO.get(0).getLon(), GetWeather.api, "metric");
            Assertions.assertNotNull(weatherDTOMock);
            Assertions.assertNotNull(weatherDTOMock.getMain());
            Assertions.assertTrue(weatherDTOMock.getWeather().length > 0);
            System.out.println(weatherDTOMock);
        }

        @Test
        @DisplayName("Получение погоды по координатам")
        public void getWeather() {
            Mockito.when(getWeather.getWeather(weatherCityDTO.get(0).getLat(), weatherCityDTO.get(0).getLon(), GetWeather.api, "metric")).thenReturn(weatherDTO);
            WeatherDTO weatherDTO = getWeather.getWeather(55.7504461, 37.6174943, GetWeather.api, "metric");
            Assertions.assertNotNull(weatherDTO);
            Assertions.assertEquals(11.6, weatherDTO.getMain().getTemp());
            System.out.println(weatherDTO);
        }

        @Test
        @DisplayName("Получение координат по названию города")
        public void getCity() {
            Mockito.when(getCityWeather.getGeoByCity(city, 1, GetCityWeather.api)).thenReturn(weatherCityDTO);
            List<WeatherCityDTO> weatherCityDTO = getCityWeather.getGeoByCity(city, 1, GetCityWeather.api);
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
            Mockito.when(getCityWeather.getGeoByCity(city, 1, GetCityWeather.api)).thenReturn(weatherCityDTO);
            Mockito.when(getWeather.getWeather(weatherCityDTO.get(0).getLat(), weatherCityDTO.get(0).getLon(), GetWeather.api, "metric")).thenReturn(weatherDTO);
            ResponseEntity<?> response = topicsService.getWeatherInCity(city);
            Assertions.assertNotNull(response);
            Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
            System.out.println(topicsService.getWeatherInCity(city));
        }

        @Test
        @DisplayName("Не существующий город")
        public void serviceIncorrect() {
            city = "Неизвестный";
            Mockito.when(getCityWeather.getGeoByCity(city, 1, GetCityWeather.api)).thenReturn(new ArrayList<>());
            ResponseEntity<?> response = topicsService.getWeatherInCity(city);
            Assertions.assertNotNull(response);
            Assertions.assertTrue(response.getStatusCode().is4xxClientError());
            System.out.println(topicsService.getWeatherInCity(city));
        }
    }
}
