package com.sparta.duopleaseduo.entity;

import com.sparta.duopleaseduo.dto.request.CommentRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@Table(name = "comment")
@NoArgsConstructor
public class Comment extends Timestamped{ //extends Timestamped

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "comment", nullable = false)
    @NotNull
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "feed_id")
    private Feed feed;

    public Comment(CommentRequestDto requestDto) {
        this.comment = requestDto.getComment();
    }
    public Comment(CommentRequestDto requestDto,User user, Feed feed) {
        this.comment = requestDto.getComment();
        this.user = user;
        this.feed = feed;
    }

    public void update(CommentRequestDto requestDto) {
        this.comment = requestDto.getComment();
    }
}
