package com.example.demo.impl;

import com.example.demo.dto.QuestionsDTO;
import com.example.demo.dto.TopicsDTO;
import com.example.demo.dto.extended.ExtendedTopicsDTO;
import com.example.demo.dto.extended.ExtendedQuestions;
import com.example.demo.model.Questions;
import com.example.demo.model.Reactions;
import com.example.demo.model.Topics;
import com.example.demo.repository.QuestionsRepository;
import com.example.demo.repository.ReactionsRepository;
import com.example.demo.repository.TopicsRepository;
import com.example.demo.service.TopicsService;
import jakarta.validation.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class topicsServiceImpl implements TopicsService {
    private final ReactionsRepository reactionsRepository;
    private Validator validator;
    private TopicsRepository topicsRepository;
    private QuestionsRepository questionsRepository;
    public Page<TopicsDTO> convertToPageDto(Page<Topics> topicsPage) {
        Page<TopicsDTO> dtoPage = topicsPage.map(topics -> {
            TopicsDTO dto = new TopicsDTO();
            dto.setId(topics.getId());
            dto.setTitle(topics.getTitle());
            dto.setDescription(topics.getDescription());
            dto.setParentId(topics.getParentId().getId());
            dto.setCreated_at(topics.getCreated_at());
            dto.setUpdated_at(topics.getUpdated_at());
            return dto;
        });
        return dtoPage;
    }
    public TopicsDTO convertToDto(Topics topics) {
        TopicsDTO dto = new TopicsDTO();
        dto.setId(topics.getId());
        dto.setTitle(topics.getTitle());
        dto.setDescription(topics.getDescription());
        dto.setParentId(topics.getParentId().getId());
        dto.setCreated_at(topics.getCreated_at());
        dto.setUpdated_at(topics.getUpdated_at());
        return dto;
    }
    @Override
    public Page<TopicsDTO> findAll(Pageable pageable) {
        return convertToPageDto(topicsRepository.findAll(pageable));
    }

    @Override
    public Page<TopicsDTO> findAllByTitleContainingIgnoreCase(String title, Pageable pageable) {
        return convertToPageDto(topicsRepository.findAllByTitleContainingIgnoreCase(title, pageable));
    }

    @Override
    public TopicsDTO saveTopics(TopicsDTO topicsDTO) {
        Topics topics = new Topics();
        topics.setDescription(topicsDTO.getDescription());
        topics.setTitle(topicsDTO.getTitle());
        if (topicsDTO.getParentId() == null){
            topics.setParentId(null);
        }
        else {
           topics.setParentId(topicsRepository.findById(Integer.valueOf(topicsDTO.getParentId())).get());
        }
        return convertToDto(topicsRepository.save(topics));
    }

    @Override
    public TopicsDTO updateTopics(TopicsDTO topicsDTO) {
        Optional<Topics> topics = topicsRepository.findById(topicsDTO.getId());
            topics.get().setParentId(topicsRepository.findById(topicsDTO.getParentId()).get());
            topics.get().setDescription(topicsDTO.getDescription());
        topics.get().setTitle(topicsDTO.getTitle());
        return convertToDto(topicsRepository.save(topics.get()));
    }

    @Override
    public TopicsDTO patchTopics(HashMap<String, String> body,Integer id) {
        TopicsDTO topicsDTO = new TopicsDTO();
        Optional<Topics> topics = topicsRepository.findById(id);
        topicsDTO.setId(id);
        topicsDTO.setParentId(topics.get().getParentId().getId());
        topicsDTO.setTitle(topics.get().getTitle());
        topicsDTO.setDescription(topics.get().getDescription());
        if (body.containsKey("title") && body.get("title") != null){
            topicsDTO.setTitle(body.get("title"));
        }
        if (body.containsKey("description") && body.get("description") != null){
            topicsDTO.setDescription(body.get("description"));
        }
        if (body.containsKey("parentId")){
            topicsDTO.setParentId(Integer.valueOf(body.get("parentId")));
        }
        Set<ConstraintViolation<TopicsDTO>> violations = validator.validate(topicsDTO);
        if (!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }
        topics.get().setParentId(topicsRepository.findById(topicsDTO.getParentId()).get());
        topics.get().setTitle(topicsDTO.getTitle());
        topics.get().setDescription(topicsDTO.getDescription());
        topicsRepository.save(topics.get());
        return topicsDTO;
    }

    @Override
    public TopicsDTO deleteTopics(Integer id) {
            Topics topics = topicsRepository.findById(id).get();
            topicsRepository.deleteById(id);
            return convertToDto(topics);
    }

    @Override
    public TopicsDTO findById(Integer id) {
        Topics topics = topicsRepository.findById(id).orElse(null);
        if(topics == null){
            return null;
        }
        return convertToDto(topics);
    }

    @Override
    public ExtendedTopicsDTO findByIdExtended(Integer id) {
        Optional<Topics> topics = topicsRepository.findById(id);
        ExtendedTopicsDTO extendedTopicsDTO = null;
        if (topics.isPresent()){
            List<Questions> questionsList = questionsRepository.findByTopicId(topics.get());
            if (!questionsList.isEmpty()){
                List<ExtendedQuestions> extendedQuestionsList = new ArrayList<>();
                List<Reactions> reactionsList;
                for(Questions questions: questionsList){
                    reactionsList = reactionsRepository.findAllByQuestionsId(questions);
                    extendedQuestionsList.add(new ExtendedQuestions(questions.getId(),questions.getQuestion(),questions.getAnswer(),questions.is_popular(),reactionsList));
                }
                extendedTopicsDTO = new ExtendedTopicsDTO(topics.get(),extendedQuestionsList);
            }
            else {
                extendedTopicsDTO = new ExtendedTopicsDTO(topics.get());
            }
        }
        else {
            return null;
        }
        return extendedTopicsDTO;
    }

}
