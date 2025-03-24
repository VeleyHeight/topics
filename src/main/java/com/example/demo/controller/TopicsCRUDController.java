package com.example.demo.controller;

import com.example.demo.client.GetCityWeather;
import com.example.demo.client.GetWeather;
import com.example.demo.dto.TopicsDTO;
import com.example.demo.dto.WeatherCityDTO;
import com.example.demo.dto.WeatherDTO;
import com.example.demo.filter.TopicsFilter;
import com.example.demo.model.Topics;
import com.example.demo.repository.TopicsRepository;
import com.example.demo.service.TopicsService;
import jakarta.validation.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.boot.context.properties.bind.Nested;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/topics")
public class TopicsCRUDController {
    private final TopicsService topicsService;

    @GetMapping("/weather")
    public ResponseEntity<?> getWeatherInCity(@Valid @RequestParam @NotBlank @NotNull String city) {
        return topicsService.getWeatherInCity(city);
    }

    @GetMapping
    public ResponseEntity<PagedModel<TopicsDTO>> getAllTopics(@ModelAttribute @ParameterObject TopicsFilter filter, @PageableDefault @ParameterObject Pageable pageable) {
        return ResponseEntity.ok(new PagedModel<>(topicsService.findAll(filter, pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTopicsById(@PathVariable Integer id) {
        return ResponseEntity.ok(topicsService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> createTopics(@Valid @RequestBody TopicsDTO topicsDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(topicsService.saveTopics(topicsDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTopics(@PathVariable Integer id, @Valid @RequestBody TopicsDTO topicsDTO) {
        return ResponseEntity.ok(topicsService.updateTopics(id, topicsDTO));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchTopics(@PathVariable Integer id, @RequestBody TopicsFilter topicsFilter) {
        return ResponseEntity.status(HttpStatus.OK).body(topicsService.patchTopics(topicsFilter, id));
    }

    @DeleteMapping("/{id}")
    public void deleteTopics(@PathVariable Integer id) {
        topicsService.deleteTopics(id);
    }

    @GetMapping("/extended/{id}")
    public ResponseEntity<?> getTopicsByIdExtended(@PathVariable Integer id) {
        return ResponseEntity.ok(topicsService.findByIdExtended(id));
    }

}
