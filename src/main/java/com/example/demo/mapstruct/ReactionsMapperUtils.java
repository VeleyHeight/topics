package com.example.demo.mapstruct;

import com.example.demo.model.Questions;
import com.example.demo.repository.QuestionsRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;


@Component
@Named("ReactionsMapperUtils")
@RequiredArgsConstructor
public class ReactionsMapperUtils {
    @Autowired
    QuestionsRepository questionsRepository;

    @Named("createQuestionsToReactions")
    public Questions createQuestionsToReactions(Integer id){
        if(id == null){
            return null;
        }
       return questionsRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Questions not found with ID: " + id));
    }
}
