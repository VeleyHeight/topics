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
import java.util.List;

@Entity
@Setter
@Getter
@Table
public class Questions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    //todo !!! коллекции не используйются с типом EAGER тк может быть огромное количество записей в ней
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "questionsId", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Reactions> reactions;
    //todo LAZY
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "topic_id", nullable = false)
    private Topics topicId;
}
