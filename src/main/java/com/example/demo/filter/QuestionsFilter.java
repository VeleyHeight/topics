package com.example.demo.filter;

import com.example.demo.dto.QuestionsDTO;
import com.example.demo.model.Questions;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public record QuestionsFilter(String question, String answer, String is_popular, String topicId) {
    public QuestionsDTO newPatchDTO(){
        try{

            return new QuestionsDTO(question,
                    answer,
                    (is_popular == null)?(false):(Boolean.parseBoolean(is_popular)),
                    (topicId == null)?(null):(Integer.parseInt(topicId)),
                    null,
                    null);
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Incorrect is_popular or topicId");
        }
    }

    public Specification<Questions> specification(){
        return Specification.where(containingQuestions()).and(containingAnswer()).and(containingIsPopular().and(containingTopicId()));
    }
    private Specification<Questions> containingQuestions(){
        return ((root, query, criteriaBuilder) -> {
            if (question == null){
                return null;
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("question")),"%"+question.toLowerCase()+"%");
        });
    }
    private Specification<Questions> containingAnswer(){
        return ((root, query, criteriaBuilder) -> {
          if (answer == null){
              return null;
          }
          return criteriaBuilder.like(criteriaBuilder.lower(root.get("answer")),"%"+answer+"%");
        });
    }
    private Specification<Questions> containingIsPopular(){
        return ((root, query, criteriaBuilder) -> {
            if(is_popular == null){
                return null;
            }
            try{
                return criteriaBuilder.equal(root.get("is_popular"),Boolean.parseBoolean(is_popular));
            }
            catch (Exception e){
                return criteriaBuilder.conjunction();
            }
        });
    }
    private Specification<Questions> containingTopicId(){
        return ((root, query, criteriaBuilder) -> {
            if(topicId == null){
                return null;
            }
            else if (topicId.equalsIgnoreCase("null")) {
                return criteriaBuilder.isNull(root.get("topicId"));
            }
            try {
                return criteriaBuilder.equal(root.get("topicId"),Integer.parseInt(topicId));
            }
            catch (Exception e){
                return criteriaBuilder.conjunction();
            }
        });
    }
}
