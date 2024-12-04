package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Setter
@Getter
@Table
public class Questions {

    @Id
    @GeneratedValue()
    private Integer id;
    @Column(columnDefinition = "TEXT")
    private String question;
    @Column(columnDefinition = "TEXT")
    private String answer;
    private boolean is_popular;
    @CreationTimestamp
    private Timestamp created_at;
    @UpdateTimestamp
    private Timestamp updated_at;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Topics topic_id;
}
