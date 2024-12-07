package com.example.demo.dto;

import lombok.Data;

@Data
public class QuestionsDTO {
    String question;
    String answer;
    boolean is_popular;
    Integer topic_id;
}
