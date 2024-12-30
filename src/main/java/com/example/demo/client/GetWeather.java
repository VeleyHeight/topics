package com.example.demo.client;

import com.example.demo.dto.WeatherDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "getWeather", url = "https://api.openweathermap.org/data/2.5/weather")
public interface GetWeather {
    @GetMapping
    WeatherDTO getWeather(@RequestParam Double lat, @RequestParam Double lon, @RequestParam String appid, @RequestParam String units);
}
