package com.example.demo.client;

import com.example.demo.dto.WeatherCityDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "cityGet", url = "http://api.openweathermap.org/geo/1.0/direct")
public interface GetCityWeather {
    @GetMapping
    List<WeatherCityDTO> getGeoByCity(@RequestParam String q, @RequestParam(required = false) Integer limit, @RequestParam String appid);
}
