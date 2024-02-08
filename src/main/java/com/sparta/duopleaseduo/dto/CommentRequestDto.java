package com.sparta.duopleaseduo.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Getter
@Setter
public class CommentRequestDto {
    private String comment;
}
