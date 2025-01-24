package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.UUID;

//todo @Builder
@Entity
@Getter
@Setter
@Table
public class Reactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //todo добавь аннотации @Column где нужно и используй его параметры для валидации полей!
    private UUID user_id;
    @Column(columnDefinition = "VARCHAR(50)")
    private String type;
    //todo @Column на create, update и валидации как в топиках
    @CreationTimestamp
    private Timestamp created_at;
    @JoinColumn(name = "questions_id", nullable = true)

    //todo используй LAZY
    @ManyToOne(fetch = FetchType.EAGER)
    private Questions questionsId;
}
