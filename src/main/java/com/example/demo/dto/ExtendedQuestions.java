package com.example.demo.dto;

import com.example.demo.model.Questions;
import com.example.demo.model.Reactions;
import jakarta.persistence.Column;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ExtendedQuestions {
    Integer id;
    String question;
    String answer;
    boolean is_popular;
    List<Reactions> reactions = new ArrayList<>();

    public ExtendedQuestions(Integer id, String question, String answer, boolean is_popular, List<Reactions> reactions) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.is_popular = is_popular;
        this.reactions = reactions;
    }
}
