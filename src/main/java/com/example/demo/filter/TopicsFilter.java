package com.example.demo.filter;

import com.example.demo.dto.TopicsDTO;
import com.example.demo.model.Topics;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public record TopicsFilter(String title, String description, String parentId) {

    public TopicsDTO newPatchDTO(Integer id) {
        try {
            Integer parentIdDTO = null;
            if (parentId != null) {
                parentIdDTO = Integer.parseInt(parentId);
                if (parentIdDTO.equals(id)) {
                    throw new RuntimeException("Parent id cannot be equals topic id");
                }
            }
            return new TopicsDTO(
                    title,
                    description,
                    parentIdDTO,
                    null,
                    null
            );
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Incorrect parent id");
        }
    }

    public Specification<Topics> specification() {
        return Specification.where(containingTitle()).and(containingDescription()).and(containingParentId());
    }

    private Specification<Topics> containingTitle() {
        return ((root, query, criteriaBuilder) -> {
            if (title == null) {
                return null;
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%");
        });
    }

    private Specification<Topics> containingDescription() {
        return ((root, query, criteriaBuilder) -> {
            if (description == null) {
                return null;
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + description.toLowerCase() + "%");
        });
    }

    private Specification<Topics> containingParentId() {
        return ((root, query, criteriaBuilder) -> {
            if (parentId == null) {
                return null;
            } else if (parentId.equalsIgnoreCase("null")) {
                return criteriaBuilder.isNull(root.get("parentId"));
            }
            try {
                return criteriaBuilder.equal(root.get("parentId").get("id"), Integer.parseInt(parentId));
            } catch (Exception e) {
                return criteriaBuilder.conjunction();
            }
        });
    }

}
