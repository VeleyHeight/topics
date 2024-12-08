package com.example.demo.repository;

import com.example.demo.dto.ExtendedDTO;
import com.example.demo.model.Topics;
import jakarta.persistence.Table;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Table(name = "topics")
public interface TopicsRepository extends JpaRepository<Topics,Integer> {
        Page<Topics> findAllByTitleContainingIgnoreCase(String title, Pageable pageable);
        @Query("SELECT t.id ,t.title,t.description,t.created_at, t.updated_at, t.parent_id, q.question, q.answer, q.is_popular" +
                " FROM Topics t LEFT JOIN Questions q ON t.id = q.topic_id.id where t.id = :id")
        List<Object[]> findByIdExtended(Integer id);

}
