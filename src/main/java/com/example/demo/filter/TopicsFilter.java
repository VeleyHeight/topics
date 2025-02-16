package com.example.demo.filter;

import com.example.demo.model.Topics;
import org.springframework.data.jpa.domain.Specification;

public record TopicsFilter(String title, String description, Integer parentId) {
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

    private Specification<Topics> containingParentId() { //todo не доделана проверка на соответствие для спецификации
        return ((root, query, criteriaBuilder) -> {
            if(parentId == null){
                return null;
            }
//            System.out.println(criteriaBuilder.isNotNull(root.get("parentId").get("id")));
            return criteriaBuilder.equal(root.get("parentId"), parentId);
        });
    }

}
