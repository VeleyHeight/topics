package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 128)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UsersRoles roles;

    @Column(nullable = false, updatable = false, insertable = false)
    @CreationTimestamp
    private Timestamp createdAt;
}
