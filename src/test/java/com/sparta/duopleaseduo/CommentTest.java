package com.sparta.duopleaseduo;

import com.sparta.duopleaseduo.dto.CommentRequestDto;
import com.sparta.duopleaseduo.service.CommentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
public class CommentTest {
    @Autowired
    private CommentService service;
    @Test
    @DisplayName("1차 캐시 : comment 저장")
    @Rollback(value = false)
    void createComment(){
        CommentRequestDto requestDto = new CommentRequestDto ();
        requestDto.setComment("1차 캐시 : comment 저장");
        //LocalDateTime localDate = LocalDateTime.now();
        //requestDto.setCreatedAt(localDate);
        service.createComment(requestDto);
    }
    @Test
    @DisplayName("2차 캐시 : comment 변경")
    @Rollback(value = false)
    void updateComment(){
        CommentRequestDto requestDto = new CommentRequestDto ();
        requestDto.setComment("2차 캐시 : comment 변경");
        //LocalDateTime localDate = LocalDateTime.now();
        //requestDto.setCreatedAt(localDate);
        service.updateComment(1L,requestDto);
    }
    //
    @Test
    @DisplayName("3차 캐시 : comment 삭제")
    @Rollback(value = false)
    void deleteComment(){
        service.deleteComment(1L);
    }
}
