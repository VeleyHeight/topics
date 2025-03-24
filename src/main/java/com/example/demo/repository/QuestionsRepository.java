package com.example.demo.repository;

import com.example.demo.model.Questions;
import com.example.demo.model.Topics;
import jakarta.persistence.Table;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.concurrent.ExecutorService;

//todo Лишняя аннотация
@Table(name = "questions")
public interface QuestionsRepository extends JpaRepository<Questions, Integer>, JpaSpecificationExecutor<Questions> {
    List<Questions> findByTopicId(Topics topicId);
}
