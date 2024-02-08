package com.sparta.duopleaseduo.dto;

import com.sparta.duopleaseduo.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentResponseDto {

    private Long id;
    // repopse Body
    private int code = 200;
    private String message;
    private String status;


    public CommentResponseDto(Comment savecomment) {
        this.id = savecomment.getId();
        this.message = "삭제 되었습니다";
        this.status = "OK";
    }
    public CommentResponseDto(int i) {
        this.code = i;
        this.message = "삭제 실패입니다";
        this.status = "NOT OK";
    }
}
