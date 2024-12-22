package com.example.demo.dto.extended;

import com.example.demo.dto.TopicsDTO;
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
    Integer parentId;
    List<ExtendedQuestions> questions = new ArrayList<>();

    public ExtendedTopicsDTO(TopicsDTO topics, List<ExtendedQuestions> questionsList) {
        this.id = topics.getId();
        this.title = topics.getTitle();
        this.description = topics.getDescription();
        this.created_at = topics.getCreated_at();
        this.updated_at = topics.getUpdated_at();
        this.parentId = topics.getParentId();
        this.questions = questionsList;
    }
    public ExtendedTopicsDTO(TopicsDTO topics){
        this.id = topics.getId();
        this.title = topics.getTitle();
        this.description = topics.getDescription();
        this.created_at = topics.getCreated_at();
        this.updated_at = topics.getUpdated_at();
        this.parentId = topics.getParentId();

    }
}
