package com.sparta.duopleaseduo;

import com.sparta.duopleaseduo.dto.CommentRequestDto;
import com.sparta.duopleaseduo.dto.CommentResponseDto;
import com.sparta.duopleaseduo.service.CommentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;


@SpringBootTest
public class CommentTest {
    @Autowired
    private CommentService service;

    /*                       성공 코드들                       */
    @Test
    @DisplayName("1차 캐시 : comment 저장")
    @Rollback(value = false)
    void createComment(){
        CommentRequestDto requestDto = new CommentRequestDto ();
        requestDto.setComment("1차 캐시 : comment 저장");

        CommentResponseDto res = service.createComment(requestDto);
        System.out.println("==========================================================================================");
        System.out.println(res.getCode());
        System.out.println(res.getStatus());
    }
    @Test
    @DisplayName("2차 캐시 : comment 변경")
    @Rollback(value = false)
    void updateComment(){
        CommentRequestDto requestDto = new CommentRequestDto ();
        requestDto.setComment("2차 캐시 : comment 변경");
        //LocalDateTime localDate = LocalDateTime.now();
        //requestDto.setCreatedAt(localDate);
        CommentResponseDto res = service.updateComment(3L,requestDto);
        System.out.println("==========================================================================================");
        System.out.println(res.getCode());
        System.out.println(res.getStatus());
    }
    //
    @Test
    @DisplayName("3차 캐시 : comment 삭제")
    @Rollback(value = false)
    void deleteComment(){
        CommentResponseDto res = service.deleteComment(2L);
        System.out.println("==========================================================================================");
        System.out.println(res.getCode());
        System.out.println(res.getStatus());
    }
    
    
    ///
    /*                       싶패 코드들                       */
//    @Test
//    @DisplayName("1차 캐시 : comment 저장")
//    @Rollback(value = false)
//    void createCommentfail(){
//        CommentRequestDto requestDto = new CommentRequestDto();
//        requestDto.setComment("1차 캐시 : comment 저장");
//
//        CommentResponseDto res = service.createComment(requestDto);
//        System.out.println("==========================================================================================");
//        System.out.println(res.getCode());
//        System.out.println(res.getStatus());
//    }
    @Test
    @DisplayName("2차 캐시 : comment 변경")
    @Rollback(value = false)
    void updateCommentfail(){
        CommentRequestDto requestDto = new CommentRequestDto ();
        requestDto.setComment("2차 캐시 : comment 변경");
        //LocalDateTime localDate = LocalDateTime.now();
        //requestDto.setCreatedAt(localDate);
        CommentResponseDto res = service.updateComment(3100L,requestDto);
        System.out.println("==========================================================================================");
        System.out.println(res.getCode());
        System.out.println(res.getStatus());
    }
    //
    @Test
    @DisplayName("3차 캐시 : comment 삭제")
    @Rollback(value = false)
    void deleteCommentfail(){
        CommentResponseDto res = service.deleteComment(900L);
        System.out.println("==========================================================================================");
        System.out.println(res.getCode());
        System.out.println(res.getStatus());
    }
}
