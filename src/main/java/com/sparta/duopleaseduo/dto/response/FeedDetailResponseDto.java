package com.sparta.duopleaseduo.dto.response;

import com.sparta.duopleaseduo.entity.Feed;
import com.sparta.duopleaseduo.entity.RiotUser;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class FeedDetailResponseDto {
    //제목, 내용 작성시간, 댓글, 좋아요 수
    private String userName;
    private Long userId;
    private String title;
    private String contents;
    private int likeCount;
    private LocalDateTime createdAt;
    private RiotUser riotUser;
    private List<CommentResponseDto> comments;

    public FeedDetailResponseDto(Feed feed, int likeCount, List<CommentResponseDto> comments) {
        this.userName = feed.getUser().getUsername();
        this.userId = feed.getUser().getId();
        this.title = feed.getTitle();
        this.contents = feed.getTitle();
        this.likeCount = likeCount;
        this.createdAt = feed.getCreateAt();
        this.comments = comments;

        this.riotUser = feed.getRiotUser();
    }
}
