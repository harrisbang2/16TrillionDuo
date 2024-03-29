package com.sparta.duopleaseduo.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Table(name = "feeds")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Feed extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "title",nullable = false)
    private String title;
    @Column(name = "content",nullable = false)
    private String content;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "riotUser_id")
    private RiotUser riotUser;

    public Feed(User user, String title, String content, RiotUser riotUser) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.riotUser = riotUser;
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }

    public boolean isUserMatch(User user){
        return this.user.equals(user);
    }
}
