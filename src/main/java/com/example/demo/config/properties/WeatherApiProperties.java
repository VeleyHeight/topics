package com.example.demo.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "openfeign.api")
public record WeatherApiProperties(
        String key,
        String geoUrl,
        String weatherUrl,
        String units
) {}
