package com.example.demo.dto;

import com.example.demo.model.Questions;
import com.example.demo.model.Topics;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Data
public class ExtendedDTO {
    Integer id;
    String title;
    String description;
    Topics parentId;
    String question;
    String answer;
    boolean is_popular;
    Timestamp created_at;
    Timestamp updated_at;


    public ExtendedDTO(Topics topics, Questions questions) {
        this.id = topics.getId();
        this.title = topics.getTitle();
        this.description = topics.getDescription();
        this.parentId = topics.getParentId();
        this.question = questions.getQuestion();
        this.answer = questions.getAnswer();
        this.is_popular = questions.is_popular();
        this.created_at = topics.getCreated_at();
        this.updated_at = topics.getUpdated_at();
    }
    public ExtendedDTO(Topics topics, List<Questions> questions) {
        this.id = topics.getId();
        this.title = topics.getTitle();
        this.description = topics.getDescription();
        this.parentId = topics.getParentId();
        this.created_at = topics.getCreated_at();
        this.updated_at = topics.getUpdated_at();
    }
}
