package com.example.demo.dto.topicsDTO;

import lombok.Data;

@Data
public class TopicsDTO {
    private Integer id;
    private String title;
    private String description;
    private Integer parentId;
}
