package com.sparta.duopleaseduo;

import com.sparta.duopleaseduo.dto.request.CommentRequestDto;
import com.sparta.duopleaseduo.exception.commentexception.CommentCreateErrorException;
import com.sparta.duopleaseduo.exception.commentexception.CommentException;
import com.sparta.duopleaseduo.exception.commentexception.CommentUpdateFailException;
import com.sparta.duopleaseduo.exception.commentexception.IncorrectUserException;
import com.sparta.duopleaseduo.exception.userexception.NoSuchUserException;
import com.sparta.duopleaseduo.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;


@SpringBootTest
public class CommentTest {
    @Autowired
    private CommentService service;

    //
    HttpServletRequest request;
    ////////////////////////////////////////////////////////////////////////////////
    /*                       성공 코드들                       */
    ////////////////////////////////////////////////////////////////////////////////
    @Test
    @DisplayName("1차 캐시 : comment 저장")
    @Rollback(value = false)
    void createComment() throws CommentCreateErrorException {
        CommentRequestDto requestDto = new CommentRequestDto ();
        requestDto.setComment("1차 캐시 : comment 저장");
        service.createComment(requestDto,1L,request);
    }


    @Test
    @DisplayName("2차 캐시 : comment 변경")
    @Rollback(value = false)
    void updateComment() throws CommentException, NoSuchUserException {
        CommentRequestDto requestDto = new CommentRequestDto ();
        requestDto.setComment("2차 캐시 : comment 변경");
        service.updateComment(1L,requestDto,request);
    }
    //
    @Test
    @DisplayName("3차 캐시 : comment 삭제")
    @Rollback(value = false)
    void deleteComment() throws NoSuchUserException, CommentException {
        service.deleteComment(1L,request);
    }
    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////
    /*                       싶패 코드들                        */
    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////
//    @Test
//    @DisplayName("1차 캐시 : comment 저장")
//    @Rollback(value = false)
//    void createCommentFail(){
//        CommentRequestDto requestDto = new CommentRequestDto();
//        requestDto.setComment("");
//
//        service.createComment(requestDto,1123L,request);
//    }
    @Test
    @DisplayName("2차 캐시 : comment 변경")
    @Rollback(value = false)
    void updateCommentFail() throws CommentException, NoSuchUserException {
        CommentRequestDto requestDto = new CommentRequestDto ();
        requestDto.setComment("2차 캐시 : comment 변경");
        service.updateComment(3100L,requestDto,request);
    }
    ///
    @Test
    @DisplayName("3차 캐시 : comment 삭제")
    @Rollback(value = false)
    void deleteCommentFail() throws NoSuchUserException, CommentException {
        service.deleteComment(900L,request);
    }


}