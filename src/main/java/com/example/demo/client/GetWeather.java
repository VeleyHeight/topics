package com.example.demo.client;

import com.example.demo.dto.WeatherDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "getWeather", url = "${openfeign.api.weather-url}")
public interface GetWeather {
    @GetMapping
    WeatherDTO getWeather(@RequestParam Double lat, @RequestParam Double lon, @RequestParam String appid, @RequestParam String units);
}
