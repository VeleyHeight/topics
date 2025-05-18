package com.example.demo.repository;

import com.example.demo.model.Questions;
import com.example.demo.model.Reactions;
import jakarta.persistence.Table;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReactionsRepository extends JpaRepository<Reactions, Integer> {
    List<Reactions> findAllByQuestionsId(Questions questions);
}
