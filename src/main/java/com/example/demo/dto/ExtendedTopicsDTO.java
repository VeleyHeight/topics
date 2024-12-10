package com.example.demo.dto;

import com.example.demo.model.Topics;
import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
public class ExtendedTopicsDTO {
    Integer id;
    String title;
    String description;
    Timestamp created_at;
    Timestamp updated_at;
    Topics parentId;
    List<ExtendedQuestions> questions = new ArrayList<>();

    public ExtendedTopicsDTO(Topics topics, List<ExtendedQuestions> questionsList) {
        this.id = topics.getId();
        this.title = topics.getTitle();
        this.description = topics.getDescription();
        this.created_at = topics.getCreated_at();
        this.updated_at = topics.getUpdated_at();
        this.parentId = topics.getParentId();
        this.questions = questionsList;
    }
    public ExtendedTopicsDTO(Topics topics){
        this.id = topics.getId();
        this.title = topics.getTitle();
        this.description = topics.getDescription();
        this.created_at = topics.getCreated_at();
        this.updated_at = topics.getUpdated_at();
        this.parentId = topics.getParentId();

    }
}
