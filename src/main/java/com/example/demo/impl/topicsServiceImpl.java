package com.example.demo.impl;

import com.example.demo.dto.extended.ExtendedTopicsDTO;
import com.example.demo.dto.extended.ExtendedQuestions;
import com.example.demo.dto.topicsDTO.TopicsDTO;
import com.example.demo.dto.topicsDTO.TopicsDTOValidation;
import com.example.demo.dto.topicsDTO.UpdateTopicdDTOValidation;
import com.example.demo.model.Questions;
import com.example.demo.model.Reactions;
import com.example.demo.model.Topics;
import com.example.demo.repository.QuestionsRepository;
import com.example.demo.repository.ReactionsRepository;
import com.example.demo.repository.TopicsRepository;
import com.example.demo.service.TopicsService;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class topicsServiceImpl implements TopicsService {
    private final ReactionsRepository reactionsRepository;
    TopicsRepository topicsRepository;
    QuestionsRepository questionsRepository;
    @Override
    public Page<Topics> findAll(Pageable pageable) {
        return topicsRepository.findAll(pageable);
    }

    @Override
    public Page<Topics> findAllByTitleContainingIgnoreCase(String title, Pageable pageable) {
        return topicsRepository.findAllByTitleContainingIgnoreCase(title, pageable);
    }

    @Override
    public Topics saveTopics(TopicsDTOValidation topicsDTOValidation) {
        Topics topics = new Topics();
        topics.setDescription(topicsDTOValidation.getDescription());
        topics.setTitle(topicsDTOValidation.getTitle());

        if (topicsDTOValidation.getParentId() == null){
            topics.setParentId(null);
        }
        else {
           topics.setParentId(topicsRepository.findById(Integer.valueOf(topicsDTOValidation.getParentId())).get());
        }
        return topicsRepository.save(topics);
    }

    @Override
    public Topics updateTopics(TopicsDTOValidation topicsDTOValidation, Integer id) {
        Optional<Topics> topics = topicsRepository.findById(id);
        topics.get().setParentId(topicsRepository.findById(Integer.parseInt(topicsDTOValidation.getParentId())).get());
        topics.get().setDescription(topicsDTOValidation.getDescription());
        topics.get().setTitle(topicsDTOValidation.getTitle());
        return topicsRepository.save(topics.get());
    }

    @Override
    public Topics patchTopics(UpdateTopicdDTOValidation topics, Integer id) {

        return null;
    }

    @Override
    public Topics deleteTopics(Integer id) {
        if (!topicsRepository.existsById(id)){
            return null;
        }
        else {
            Topics topics = topicsRepository.findById(id).get();
            topicsRepository.deleteById(id);
            return topics;
        }
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
