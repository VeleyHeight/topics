package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "openfeign.api")
public record WeatherApiProperties(
        String key,
        String geoUrl,
        String weatherUrl,
        String units
) {}
