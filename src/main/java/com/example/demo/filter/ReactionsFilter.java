package com.example.demo.filter;

import com.example.demo.dto.ReactionsDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

public record ReactionsFilter(
        String user_id,
        String type,
        String questionsId) {
    public ReactionsDTO newPatchDTO() {
        try {
            Integer idQ = null;
            UUID idU = null;
            if (questionsId != null){
              idQ = Integer.parseInt(questionsId);
            }
            if (user_id != null){
               idU = UUID.fromString(user_id);
            }
            return new ReactionsDTO(idU, type, idQ);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Incorrect data input");
        }
    }
}

