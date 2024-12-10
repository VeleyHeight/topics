package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table
public class Reactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private UUID user_id;
    @Column(columnDefinition = "VARCHAR(50)")
    private String type;
    @CreationTimestamp
    private Timestamp created_at;
    @JoinColumn(name = "questions_id",nullable = true)
    @ManyToOne(fetch = FetchType.EAGER)
    private Questions questionsId;
}
