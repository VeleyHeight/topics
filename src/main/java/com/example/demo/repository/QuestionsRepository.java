package com.example.demo.repository;

import com.example.demo.model.Questions;
import com.example.demo.model.Topics;
import jakarta.persistence.Table;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

@Table(name = "questions")
public interface QuestionsRepository extends JpaRepository<Questions, Integer> {
    Page<Questions> findAllByQuestionContainingIgnoreCase(String title, Pageable pageable);
}
