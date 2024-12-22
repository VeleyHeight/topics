package com.example.demo.dto.extended;

import com.example.demo.dto.ReactionsDTO;
import com.example.demo.model.Reactions;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ExtendedQuestions {
    Integer id;
    String question;
    String answer;
    boolean is_popular;
    List<ReactionsDTO> reactions = new ArrayList<>();

    public ExtendedQuestions(Integer id, String question, String answer, boolean is_popular, List<ReactionsDTO> reactions) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.is_popular = is_popular;
        this.reactions = reactions;
    }
}
