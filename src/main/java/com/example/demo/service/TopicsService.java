package com.example.demo.service;

import com.example.demo.dto.TopicsDTO;
import com.example.demo.dto.WeatherCityDTO;
import com.example.demo.dto.extended.ExtendedTopicsDTO;
import com.example.demo.model.Topics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public interface TopicsService {
    Page<TopicsDTO> findAll(Pageable pageable);
    Page<TopicsDTO> findAllByTitleContainingIgnoreCase(String title, Pageable pageable);
    TopicsDTO saveTopics(TopicsDTO topics);
    TopicsDTO deleteTopics(Integer id);
    TopicsDTO updateTopics(TopicsDTO topics);
    TopicsDTO patchTopics(HashMap<String,String> map, Integer id);
    TopicsDTO findById(Integer id);
    ExtendedTopicsDTO findByIdExtended(Integer id);

    ResponseEntity<?> getWeatherInCity(String city);
}
