package com.example.demo.OpenFeignTest;

import com.example.demo.client.GetCityWeather;
import com.example.demo.client.GetWeather;
import com.example.demo.config.WeatherApiProperties;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.junit.jupiter.Testcontainers;

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
    @Mock
    WeatherApiProperties weatherApiProperties1;
    WeatherApiProperties weatherApiProperties = new WeatherApiProperties("ff60c2193457fb3fc6b459ae519fb054", "http://api.openweathermap.org/geo/1.0/direct", "https://api.openweathermap.org/data/2.5/weather", "metric");

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
            response.put("Weather", weatherDTO.weather()[0].main());
            response.put("Description", weatherDTO.weather()[0].description());
            response.put("Temp", weatherDTO.main().temp());
            response.put("Pressure", weatherDTO.main().pressure());
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
            Mockito.when(getCityWeather.getGeoByCity(city, 1, weatherApiProperties.key())).thenReturn(weatherCityDTO);
            Mockito.when(getWeather.getWeather(weatherCityDTO.get(0).lat(), weatherCityDTO.get(0).lon(), weatherApiProperties.key(), weatherApiProperties.units())).thenReturn(weatherDTO);
            List<WeatherCityDTO> weatherCityDTOMock = getCityWeather.getGeoByCity(city, 1, weatherApiProperties.key());
            Assertions.assertEquals(1, weatherCityDTOMock.size());
            Assertions.assertNotNull(weatherCityDTOMock.get(0).lat());
            Assertions.assertNotNull(weatherCityDTOMock.get(0).lon());
            System.out.println(weatherCityDTO.get(0));
            WeatherDTO weatherDTOMock = getWeather.getWeather(weatherCityDTO.get(0).lat(), weatherCityDTO.get(0).lon(), weatherApiProperties.key(), weatherApiProperties.units());
            Assertions.assertNotNull(weatherDTOMock);
            Assertions.assertNotNull(weatherDTOMock.main());
            Assertions.assertTrue(weatherDTOMock.weather().length > 0);
            System.out.println(weatherDTOMock);
        }

        @Test
        @DisplayName("Получение погоды по координатам")
        public void getWeather() {
            Mockito.when(getWeather.getWeather(weatherCityDTO.get(0).lat(), weatherCityDTO.get(0).lon(), weatherApiProperties.key(), weatherApiProperties.units())).thenReturn(weatherDTO);
            WeatherDTO weatherDTO = getWeather.getWeather(55.7504461, 37.6174943, weatherApiProperties.key(), weatherApiProperties.units());
            Assertions.assertNotNull(weatherDTO);
            Assertions.assertEquals(11.6, weatherDTO.main().temp());
            System.out.println(weatherDTO);
        }

        @Test
        @DisplayName("Получение координат по названию города")
        public void getCity() {
            Mockito.when(getCityWeather.getGeoByCity(city, 1, weatherApiProperties.key())).thenReturn(weatherCityDTO);
            List<WeatherCityDTO> weatherCityDTO = getCityWeather.getGeoByCity(city, 1, weatherApiProperties.key());
            Assertions.assertTrue(!weatherCityDTO.isEmpty());
            Assertions.assertNotNull(weatherCityDTO.get(0).lat());
            Assertions.assertNotNull(weatherCityDTO.get(0).lon());
            System.out.println(weatherCityDTO.get(0));
        }
    }

    @Nested
    @DisplayName("Тестирование сервиса по получению погоды для заданного города")
    public class serviceTests {
        @Test
        @DisplayName("Существующий город")
        public void serviceCorrect() {
            Mockito.when(getCityWeather.getGeoByCity(city, 1, weatherApiProperties.key())).thenReturn(weatherCityDTO);
            Mockito.when(getWeather.getWeather(weatherCityDTO.get(0).lat(), weatherCityDTO.get(0).lon(), weatherApiProperties.key(), weatherApiProperties.units())).thenReturn(weatherDTO);
            Mockito.when(weatherApiProperties1.key()).thenReturn("ff60c2193457fb3fc6b459ae519fb054");
            Mockito.when(weatherApiProperties1.units()).thenReturn("metric");
            ResponseEntity<?> response = topicsService.getWeatherInCity(city);
            Assertions.assertNotNull(response);
            System.out.println(response.getStatusCode());
            Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
            System.out.println(topicsService.getWeatherInCity(city));
        }

        @Test
        @DisplayName("Не существующий город")
        public void serviceIncorrect() {
            city = "Неизвестный";
            Mockito.when(weatherApiProperties1.key()).thenReturn("ff60c2193457fb3fc6b459ae519fb054");
            Mockito.when(getCityWeather.getGeoByCity(city, 1, weatherApiProperties.key())).thenReturn(new ArrayList<>());
            ResponseEntity<?> response = topicsService.getWeatherInCity(city);
            Assertions.assertNotNull(response);
            System.out.println(response.getStatusCode());
            Assertions.assertTrue(response.getStatusCode().is4xxClientError());
            System.out.println(topicsService.getWeatherInCity(city));
        }
    }
}
