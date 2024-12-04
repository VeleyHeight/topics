package com.example.demo.repository;

import com.example.demo.model.Reactions;
import jakarta.persistence.Table;
import org.springframework.data.jpa.repository.JpaRepository;

@Table(name = "reactions")
public interface ReactionsRepository extends JpaRepository<Reactions, Integer> {
}
