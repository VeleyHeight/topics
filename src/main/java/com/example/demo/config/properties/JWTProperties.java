package com.example.demo.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "jwt")
public record JWTProperties(String secret,
                            Duration lifetime) {
}
