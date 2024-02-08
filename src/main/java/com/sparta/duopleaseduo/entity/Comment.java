package com.sparta.duopleaseduo.entity;

import com.sparta.duopleaseduo.dto.request.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@Table(name = "comment")

@NoArgsConstructor
public class Comment { //extends Timestamped

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "comment", nullable = false)
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    // 유저 엔티티 아직 안적어 그런듯
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "feed_id")
    private Feed feed;

    public Comment(CommentRequestDto requestDto) {
        this.user = requestDto.getUser();
        this.comment = requestDto.getComment();
        this.feed = requestDto.getFeed();
    }

    public void update(CommentRequestDto requestDto) {
        this.comment = requestDto.getComment();
    }
}
