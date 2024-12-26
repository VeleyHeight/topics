package com.example.demo.converter;

import com.example.demo.dto.QuestionsDTO;
import com.example.demo.model.Questions;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class QuestionsConverter {
    private ModelMapper modelMapper = new ModelMapper();

    public QuestionsDTO convertToDTO(Questions questions) {
        return modelMapper.map(questions, QuestionsDTO.class);
    }

    public Page<QuestionsDTO> convertToDTOPage(Page<Questions> questions) {
        return questions.map(this::convertToDTO);
    }

}
