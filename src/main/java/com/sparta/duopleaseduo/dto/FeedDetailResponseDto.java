package com.sparta.duopleaseduo.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FeedDetailResponseDto {
    //제목, 내용 작성시간, 댓글, 좋아요 수
    private String userName;
    private String title;
    private String contents;
    /*
    Todo : Type(List) Comment Response dto 반환,
           CommentResponseDto - username, content, createAt, commentListCount
     */
    private Long likeCount;
    private LocalDateTime createdAt;

}
