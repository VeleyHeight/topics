package com.example.demo.repository;

import com.example.demo.model.Questions;
import com.example.demo.model.Topics;
import jakarta.persistence.Table;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

//todo необязательная аннотация
@Table(name = "topics")
public interface TopicsRepository extends JpaRepository<Topics, Integer>, JpaSpecificationExecutor<Topics> {
    Topics findAllByQuestions(List<Questions> questions);
}
