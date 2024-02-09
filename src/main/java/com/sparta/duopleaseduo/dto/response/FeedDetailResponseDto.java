package com.sparta.duopleaseduo.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class FeedDetailResponseDto {
    //제목, 내용 작성시간, 댓글, 좋아요 수
    private String userName;
    private String title;
    private String contents;
    private Long likeCount;
    private LocalDateTime createdAt;

    private List<CommentResponseDto> comments;

    public FeedDetailResponseDto(String userName, String title, String contents, Long likeCount, LocalDateTime createdAt, List<CommentResponseDto> comments) {
        this.userName = userName;
        this.title = title;
        this.contents = contents;
        this.likeCount = likeCount;
        this.createdAt = createdAt;
        this.comments = comments;
    }
}
