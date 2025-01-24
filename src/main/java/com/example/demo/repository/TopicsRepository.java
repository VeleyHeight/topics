package com.example.demo.repository;

import com.example.demo.model.Questions;
import com.example.demo.model.Topics;
import jakarta.persistence.Table;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//todo необязательная аннотация
@Table(name = "topics")
public interface TopicsRepository extends JpaRepository<Topics, Integer> {
    Page<Topics> findAllByTitleContainingIgnoreCase(String title, Pageable pageable);

    Topics findAllByQuestions(List<Questions> questions);
}
