package com.example.demo.repository;

import com.example.demo.model.Questions;
import jakarta.persistence.Table;
import org.springframework.data.jpa.repository.JpaRepository;

@Table(name = "questions")
public interface QuestionsRepository extends JpaRepository<Questions, Integer> {
}
