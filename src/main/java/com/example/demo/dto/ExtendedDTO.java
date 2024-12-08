package com.example.demo.dto;

import com.example.demo.model.Topics;
import lombok.Data;

import java.util.List;

@Data
public class ExtendedDTO {
    String title;
    String description;
    List<Topics> parent_id;
    String created_at;
    String updated_at;
    String question;
    String answer;
    boolean is_popular;
}
