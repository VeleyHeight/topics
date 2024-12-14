package com.example.demo.impl;

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

    @Override
    public Page<Topics> findAll(Pageable pageable) {
        return topicsRepository.findAll(pageable);
    }

    @Override
    public Page<Topics> findAllByTitleContainingIgnoreCase(String title, Pageable pageable) {
        return topicsRepository.findAllByTitleContainingIgnoreCase(title, pageable);
    }

    @Override
    public Topics saveTopics(TopicsDTO topicsDTO) {
        Topics topics = new Topics();
        topics.setDescription(topicsDTO.getDescription());
        topics.setTitle(topicsDTO.getTitle());
        if (topicsDTO.getParentId() == null){
            topics.setParentId(null);
        }
        else {
           topics.setParentId(topicsRepository.findById(Integer.valueOf(topicsDTO.getParentId())).get());
        }
        return topicsRepository.save(topics);
    }

    @Override
    public Topics updateTopics(TopicsDTO topicsDTO) {
        Optional<Topics> topics = topicsRepository.findById(topicsDTO.getId());
            topics.get().setParentId(topicsRepository.findById(topicsDTO.getParentId()).get());
            topics.get().setDescription(topicsDTO.getDescription());
        topics.get().setTitle(topicsDTO.getTitle());
        return topicsRepository.save(topics.get());
    }

    @Override
    public Topics patchTopics(HashMap<String, String> body,Integer id) {
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
            System.out.println(topicsDTO.getParentId());
            throw new ConstraintViolationException(violations);
        }

        topics.get().setParentId(topicsRepository.findById(topicsDTO.getParentId()).get());
        topics.get().setTitle(topicsDTO.getTitle());
        topics.get().setDescription(topicsDTO.getDescription());
        return topicsRepository.save(topics.get());
    }

    @Override
    public Topics deleteTopics(Integer id) {
            Topics topics = topicsRepository.findById(id).get();
            topicsRepository.deleteById(id);
            return topics;
    }


    @Override
    public Topics findById(Integer id) {
        return topicsRepository.findById(id).orElse(null);
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
