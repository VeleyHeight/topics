package com.example.demo.impl;

import com.example.demo.dto.ExtendedDTO;
import com.example.demo.dto.TopicsDTO;
import com.example.demo.model.Topics;
import com.example.demo.repository.TopicsRepository;
import com.example.demo.service.TopicsService;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class topicsServiceImpl implements TopicsService {
    TopicsRepository topicsRepository;
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
        topics.setParent_id(null);
        if (topicsDTO.getParent_id() != null) {
            Optional<Topics> topicsOptional = topicsRepository.findById(Integer.valueOf(topicsDTO.getParent_id()));
            if (topicsOptional.isPresent()) {
                topics.setParent_id(topicsOptional.get());
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
    public List<Object[]> findByIdExtended(Integer id) {
        return topicsRepository.findByIdExtended(id);
    }
}
