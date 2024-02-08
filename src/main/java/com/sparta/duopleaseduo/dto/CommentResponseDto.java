package com.sparta.duopleaseduo.dto;

import com.sparta.duopleaseduo.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class CommentResponseDto {

    private Long id;
    private String comment;



    public CommentResponseDto(Comment savecomment) {
        this.id = savecomment.getId();
        this.comment = savecomment.getComment();
    }
}
