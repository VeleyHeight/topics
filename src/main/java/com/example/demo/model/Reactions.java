package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true)
    private UUID user_id;
    @Column(columnDefinition = "VARCHAR(50)", nullable = false)
    private String type;
    @Column(insertable = false, updatable = false, nullable = false)
    @CreationTimestamp
    private Timestamp created_at;
    @JoinColumn(name = "questions_id", nullable = true)
    @ManyToOne(fetch = FetchType.LAZY)
    private Questions questionsId;
}
