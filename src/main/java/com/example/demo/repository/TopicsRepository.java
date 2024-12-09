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
        @Query("SELECT t FROM Topics t LEFT JOIN FETCH t.questions q LEFT join fetch q.reactions WHERE t.id = :id")
        List<Topics> findByIdExtended(Integer id);
}
