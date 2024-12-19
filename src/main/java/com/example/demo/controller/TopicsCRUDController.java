package com.example.demo.controller;

import com.example.demo.client.GetCityWeather;
import com.example.demo.client.GetWeather;
import com.example.demo.dto.TopicsDTO;
import com.example.demo.dto.WeatherCityDTO;
import com.example.demo.dto.WeatherDTO;
import com.example.demo.model.Topics;
import com.example.demo.service.TopicsService;
import jakarta.validation.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

    @RestController
    @AllArgsConstructor
    @RequestMapping("/topics")
    public class TopicsCRUDController {
        private final TopicsService topicsService;
        private final GetCityWeather getCityWeather;
        private final GetWeather getWeather;
        @GetMapping("/weather")
        public ResponseEntity<?> getCity(@Valid @RequestParam @NotBlank @NotNull String city) {
            try {
                List<WeatherCityDTO> weatherCityDTOList = getCityWeather.getGeoByCity(city, 1, GetCityWeather.api);
                if (weatherCityDTOList != null && !weatherCityDTOList.isEmpty()) {
                    Double latitude = weatherCityDTOList.get(0).getLat();
                    Double longitude = weatherCityDTOList.get(0).getLon();
                    try {
                        WeatherDTO weatherDTOList = getWeather.getWeather(latitude, longitude, GetWeather.api, "metric");
                        if (weatherDTOList != null) {
                            HashMap<String, Object> response = new HashMap<>();
                            response.put("Weather", weatherDTOList.getWeather()[0].getMain());
                            response.put("Description", weatherDTOList.getWeather()[0].getDescription());
                            response.put("Temp", weatherDTOList.getMain().getTemp());
                            response.put("Pressure", weatherDTOList.getMain().getPressure());
                            return ResponseEntity.ok(response);
                        }
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Weather not found");
                    }
                    catch (Exception e){
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error getting weather for the entered city");
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("City not found");
                }
            }
            catch (Exception e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error getting geo coordinates for the entered city");
            }
        }
        @GetMapping
        public ResponseEntity<Page<TopicsDTO>> getAllTopics(@RequestParam(required = false) String title,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size) {
            if (title != null && !title.isEmpty()) {
                return ResponseEntity.ok(topicsService.findAllByTitleContainingIgnoreCase(title, PageRequest.of(page, size)));
            }
            return ResponseEntity.ok(topicsService.findAll(PageRequest.of(page, size)));
        }
        @GetMapping("/{id}")
        public ResponseEntity<?> getTopicsById(@PathVariable Integer id) {
            if (topicsService.findById(id) == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Topic with this id is not exist");
            }
            else {
                return ResponseEntity.ok(topicsService.findById(id));
            }
        }
        @PostMapping
        public ResponseEntity<?> createTopics(@Valid @RequestBody TopicsDTO topicsDTO) {
                return ResponseEntity.status(HttpStatus.CREATED).body(topicsService.saveTopics(topicsDTO));
        }
        @PutMapping("/{id}")
        public ResponseEntity<?> updateTopics(@PathVariable Integer id,  @Valid @RequestBody TopicsDTO topicsDTO) {
            if (topicsDTO.getParentId().equals(id)){
                return ResponseEntity.badRequest().body("Parent id cannot be equals topic id");
            }
            if (topicsService.findById(id) == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Topic with this id is not exist");
            }
                topicsDTO.setId(id);
                return ResponseEntity.ok(topicsService.updateTopics(topicsDTO));
        }
        @PatchMapping("/{id}")
        public ResponseEntity<?> patchTopics(@PathVariable Integer id, @RequestBody HashMap<String,String> body) {
            if (topicsService.findById(id) == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Topic with this id is not exist");
            }
                if (!body.containsKey("parentId") && !body.containsKey("title") && !body.containsKey("description")){
                    return ResponseEntity.badRequest().body("Input is empty");
                }

            if (body.containsKey("parentId") && body.get("parentId").equals(id.toString())){
                return ResponseEntity.badRequest().body("Parent id cannot be equals topic id");
            }
            TopicsDTO topics;
            try {
                topics = topicsService.patchTopics(body,id);
            }
            catch (Exception e ){
                return ResponseEntity.badRequest().body(e.getMessage());
            }
            return ResponseEntity.status(HttpStatus.OK).body(topics);
        }
        @DeleteMapping("/{id}")
        public ResponseEntity<?> deleteTopics(@PathVariable Integer id) {
            if (topicsService.findById(id) == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Topic with this id does not exist");
            }
            else {
                return ResponseEntity.status(HttpStatus.OK).body(topicsService.deleteTopics(id));
            }
        }
        @GetMapping("/extended/{id}")
        public ResponseEntity<?> getTopicsByIdExtended(@PathVariable Integer id) {
            if (topicsService.findById(id) == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Topic with this id does not exist");
            }
            else {
                return ResponseEntity.ok(topicsService.findByIdExtended(id));
            }
        }
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
            Map<String, String> errors = new HashMap<>();
            ex.getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
            return errors;
        }
    }
