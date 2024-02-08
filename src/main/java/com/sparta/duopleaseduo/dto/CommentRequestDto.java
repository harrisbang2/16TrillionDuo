package com.sparta.duopleaseduo.dto;

import com.sparta.duopleaseduo.entity.Feed;
import com.sparta.duopleaseduo.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Getter
@Setter
public class CommentRequestDto {
    private User user;
    private Feed feed;
    private String comment;
}
