package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Questions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String question;
    @Column(columnDefinition = "TEXT")
    private String answer;
    @Column(name = "is_popular", nullable = false)
    @Builder.Default
    private Boolean is_popular = false;
    @Column(insertable = false, nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp created_at;
    @Column(insertable = false, nullable = true)
    @UpdateTimestamp
    private Timestamp updated_at;
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "questionsId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Reactions> reactions;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", nullable = false)
    private Topics topicId;
}
