package com.example.demo.impl;

import com.example.demo.dto.ExtendedTopicsDTO;
import com.example.demo.dto.ExtendedQuestions;
import com.example.demo.dto.TopicsDTO;
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
    public Topics saveTopics(TopicsDTO topicsDTO) {
        Topics topics = new Topics();
        topics.setDescription(topicsDTO.getDescription());
        topics.setTitle(topicsDTO.getTitle());
        topics.setParentId(null);
        if (topicsDTO.getParentId() != null) {
            Optional<Topics> topicsOptional = topicsRepository.findById(topicsDTO.getParentId());
            if (topicsOptional.isPresent()) {
                topics.setParentId(topicsOptional.get());
            }
            else {
                throw new EmptyResultDataAccessException(1);
            }
        }
        return topicsRepository.save(topics);
    }

    @Override
    public String deleteTopics(Integer id) {
        topicsRepository.deleteById(id);
        return "Topic deleted successfully";
    }

    @Override
    public Topics updateTopics(Topics topics) {
        return topicsRepository.save(topics);
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
