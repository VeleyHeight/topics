package com.example.demo.mapstruct;

import com.example.demo.model.Topics;
import com.example.demo.repository.TopicsRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@Named("TopicsMapperUtils")
@RequiredArgsConstructor
public class TopicsMapperUtils {
    @Autowired
    public TopicsRepository topicsRepository;
    @Named("createTopicsFromParent")
    public Topics createTopicsFromParent(Integer id){
        if(id == null){
            return null;
        }
        else {
            return topicsRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Topic with this id is not exist"));
        }

    }
}
