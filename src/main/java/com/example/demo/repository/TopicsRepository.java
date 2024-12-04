package com.example.demo.repository;

import com.example.demo.model.Topics;
import jakarta.persistence.Table;
import org.springframework.data.jpa.repository.JpaRepository;

@Table(name = "topics")
public interface TopicsRepository extends JpaRepository<Topics,Integer> {
}
