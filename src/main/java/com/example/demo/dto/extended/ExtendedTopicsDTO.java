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

    public ExtendedTopicsDTO(Integer id, TopicsDTO topics, List<ExtendedQuestions> questionsList) {
        this.id = id;
        this.title = topics.title();
        this.description = topics.description();
        this.created_at = topics.created_at();
        this.updated_at = topics.updated_at();
        this.parentId = topics.parentId();
        this.questions = questionsList;
    }

    public ExtendedTopicsDTO(Integer id,TopicsDTO topics) {
        this.id = id;
        this.title = topics.title();
        this.description = topics.description();
        this.created_at = topics.created_at();
        this.updated_at = topics.updated_at();
        this.parentId = topics.parentId();

    }
}
