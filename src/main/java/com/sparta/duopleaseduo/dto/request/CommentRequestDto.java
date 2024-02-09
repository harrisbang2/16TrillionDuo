package com.sparta.duopleaseduo.dto.request;

import com.sparta.duopleaseduo.entity.Feed;
import com.sparta.duopleaseduo.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Getter
@Setter
public class CommentRequestDto {
    @NotBlank
    private String comment;
}
