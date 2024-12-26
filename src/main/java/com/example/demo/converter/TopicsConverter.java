package com.example.demo.converter;

import com.example.demo.dto.TopicsDTO;
import com.example.demo.model.Topics;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class TopicsConverter {

    private ModelMapper modelMapper = new ModelMapper();

    public TopicsDTO convertToDTO(Topics topics) {
        return modelMapper.map(topics, TopicsDTO.class);
    }

    public Page<TopicsDTO> convertToDTOPage(Page<Topics> topicsPage) {
        return topicsPage.map(this::convertToDTO);
    }

}
