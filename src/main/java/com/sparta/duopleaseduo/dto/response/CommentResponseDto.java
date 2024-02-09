package com.sparta.duopleaseduo.dto.response;

import com.sparta.duopleaseduo.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentResponseDto {

    private Long id;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;



    public CommentResponseDto(Comment savecomment) {
        this.id = savecomment.getId();
        this.comment = savecomment.getComment();
        this.createdAt = savecomment.getCreateAt();
        this.modifiedAt = savecomment.getUpdateAt();
    }
}
