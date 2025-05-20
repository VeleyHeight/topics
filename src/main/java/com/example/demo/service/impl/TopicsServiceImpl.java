package com.example.demo.service.impl;

import com.example.demo.client.GetCityWeather;
import com.example.demo.client.GetWeather;
import com.example.demo.config.properties.WeatherApiProperties;
import com.example.demo.dto.ReactionsDTO;
import com.example.demo.dto.TopicsDTO;
import com.example.demo.dto.WeatherCityDTO;
import com.example.demo.dto.WeatherDTO;
import com.example.demo.dto.extended.ExtendedTopicsDTO;
import com.example.demo.dto.extended.ExtendedQuestions;
import com.example.demo.filter.TopicsFilter;
import com.example.demo.mapstruct.ReactionsMapper;
import com.example.demo.mapstruct.TopicsMapper;
import com.example.demo.model.Questions;
import com.example.demo.model.Topics;
import com.example.demo.repository.QuestionsRepository;
import com.example.demo.repository.ReactionsRepository;
import com.example.demo.repository.TopicsRepository;
import com.example.demo.service.TopicsService;
import jakarta.validation.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TopicsServiceImpl implements TopicsService {
    //todo !!!оставить только поля топиков, остальное убрать, конвертер замени на Mapper, используй mapstruct
    private final Validator validator;
    private final ReactionsRepository reactionsRepository;
    private final ReactionsMapper reactionsMapper;
    private final QuestionsRepository questionsRepository;
    private final TopicsRepository topicsRepository;
    private final GetCityWeather getCityWeather;
    private final GetWeather getWeather;
    private final TopicsMapper topicsMapper;
    private final WeatherApiProperties weatherApiProperties;
    
    @Override
    public Page<TopicsDTO> findAll(TopicsFilter filter, Pageable pageable) {
        return topicsRepository.findAll(filter.specification(),pageable).map(topicsMapper::toTopicsDTO);
    }

    @Override
    public TopicsDTO saveTopics(TopicsDTO topicsDTO) {
        log.info("Начало создания топика: {}", topicsDTO);
        Topics topics = topicsMapper.toTopics(topicsDTO);
        var resultTopics = topicsRepository.save(topics);
        log.info("Топик создан успешно {}", resultTopics);
        return topicsMapper.toTopicsDTO(resultTopics);
    }

    @Override
    public TopicsDTO updateTopics(Integer id, TopicsDTO topicsDTO) {
        Topics topics = topicsRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Topic not found with ID: " + id));
        if (topicsDTO.parentId() != null && topicsDTO.parentId().equals(id)) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Parent id cannot be equals topic id");
        }
        topicsMapper.updateWithNull(topicsDTO, topics);
        return topicsMapper.toTopicsDTO(topicsRepository.save(topics));
    }
    @Override
    public void deleteTopics(Integer id) {
        if(topicsRepository.existsById(id)){
            topicsRepository.deleteById(id);
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Topic not found with ID: " + id);
        }
    }

    @Override
    public TopicsDTO findById(Integer id) {
        Topics topics = topicsRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Topic not found with ID: " + id));
        return topicsMapper.toTopicsDTO(topics);
    }

    @Override
    public TopicsDTO patchTopics(TopicsFilter topicsFilter, Integer id) {
        Topics topics = topicsRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Topic not found with ID: " + id));
        topicsMapper.patchingUpdate(topicsFilter.newPatchDTO(id),topics);
        Set<ConstraintViolation<Topics>> violations = validator.validate(topics);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        topicsRepository.save(topics);
        return topicsMapper.toTopicsDTO(topics);
    }

    @Override
    public ExtendedTopicsDTO findByIdExtended(Integer id) {
        Topics topics = topicsRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Topic not found with ID: " + id));
        ExtendedTopicsDTO extendedTopicsDTO = null;
            List<Questions> questionsList = questionsRepository.findByTopicId(topics);
            if (!questionsList.isEmpty()) {
                List<ExtendedQuestions> extendedQuestionsList = new ArrayList<>();
                List<ReactionsDTO> reactionsList;
                for (Questions questions : questionsList) {
                    reactionsList = (reactionsRepository.findAllByQuestionsId(questions).stream().map(reactionsMapper::toReactionsDTO).collect(Collectors.toList()));
                    extendedQuestionsList.add(new ExtendedQuestions(questions.getId(), questions.getQuestion(), questions.getAnswer(), questions.getIs_popular(), reactionsList));
                }
                extendedTopicsDTO = new ExtendedTopicsDTO(topics.getId(),topicsMapper.toTopicsDTO(topics), extendedQuestionsList);
            } else {
                extendedTopicsDTO = new ExtendedTopicsDTO(topics.getId(), topicsMapper.toTopicsDTO(topics));
            }
        return extendedTopicsDTO;
    }

    @Override
    public ResponseEntity<?> getWeatherInCity(String city) {
        try {
            List<WeatherCityDTO> weatherCityDTOList = getCityWeather.getGeoByCity(city, 1, weatherApiProperties.key());
            if (weatherCityDTOList != null && !weatherCityDTOList.isEmpty()) {
                try {
                    WeatherDTO weatherDTO = getWeather.getWeather(weatherCityDTOList.get(0).lat(), weatherCityDTOList.get(0).lon(), weatherApiProperties.key(), weatherApiProperties.units());
                    if (weatherDTO != null) {
                        HashMap<String, Object> response = new HashMap<>();
                        response.put("Weather", weatherDTO.weather()[0].main());
                        response.put("Description", weatherDTO.weather()[0].description());
                        response.put("Temp", weatherDTO.main().temp());
                        response.put("Pressure", weatherDTO.main().pressure());
                        return ResponseEntity.ok(response);
                    }
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Weather not found");
                } catch (Exception e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error getting weather for the entered city");
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("City not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error getting geo for the entered city");
        }
    }
}
