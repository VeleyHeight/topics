package com.example.demo.converter;

import com.example.demo.dto.ReactionsDTO;
import com.example.demo.model.Reactions;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class ReactionsConverter {
    private ModelMapper modelMapper = new ModelMapper();

    public ReactionsDTO convertToDTO(Reactions reactions){
        return modelMapper.map(reactions,ReactionsDTO.class);
    }

    public List<ReactionsDTO> convertToListDTO(List<Reactions> reactionsList){
        List<ReactionsDTO> dtoList = new ArrayList<>();
        for (Reactions reaction : reactionsList) {
            dtoList.add(convertToDTO(reaction));
        }
        return dtoList;
    }
}
