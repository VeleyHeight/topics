package com.example.demo.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

//todo добавь @Builder и используй в маппере только его! @Table излишне
@Getter
@Setter
@Entity
@Table
public class Topics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(columnDefinition = "VARCHAR(255)")
    private String title;
    @Column(columnDefinition = "VARCHAR(255)")
    private String description;
    //todo используй LAZY, nullable по дефолту true
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id", nullable = true)
    private Topics parentId;
    @OneToMany(mappedBy = "topicId", cascade = CascadeType.ALL)
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Questions> questions;
    //todo поле сделать - неизменяемое, обязательное, запрещено для вставки из приложения, генерится на стороне бд или исклбючительно контекстом персистенции!!!
    @CreationTimestamp
    private Timestamp created_at;
    //todo запрет на вставку, может быть null
    @UpdateTimestamp
    private Timestamp updated_at;
}
