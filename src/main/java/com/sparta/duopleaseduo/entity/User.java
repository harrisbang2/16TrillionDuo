package com.sparta.duopleaseduo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "introduce")
    private String introduce;

    public User(String email, String password, String username, String introduce) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.introduce = introduce;
    }
}
