package com.sparta.duopleaseduo;

import com.sparta.duopleaseduo.dto.request.CommentRequestDto;
import com.sparta.duopleaseduo.entity.Feed;
import com.sparta.duopleaseduo.entity.User;
import com.sparta.duopleaseduo.repository.FeedRepository;
import com.sparta.duopleaseduo.repository.UserRepository;
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
    @Autowired
    private UserRepository userrepo;
    @Autowired
    private FeedRepository feedrepo;

    //
    HttpServletRequest request;
    ////////////////////////////////////////////////////////////////////////////////
    /*                       성공 코드들                       */
    ////////////////////////////////////////////////////////////////////////////////
    @Test
    @DisplayName("1차 캐시 : comment 저장")
    @Rollback(value = false)
    void createComment(){
        CommentRequestDto requestDto = new CommentRequestDto ();
        requestDto.setComment("1차 캐시 : comment 저장");
        service.createComment(requestDto,1L,request);
    }


    @Test
    @DisplayName("2차 캐시 : comment 변경")
    @Rollback(value = false)
    void updateComment(){
        CommentRequestDto requestDto = new CommentRequestDto ();
        requestDto.setComment("2차 캐시 : comment 변경");
        service.updateComment(1L,requestDto,request);
    }
    //
    @Test
    @DisplayName("3차 캐시 : comment 삭제")
    @Rollback(value = false)
    void deleteComment(){
        service.deleteComment(1L,request);
    }
    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////
    /*                       싶패 코드들                       */
    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////
    @Test
    @DisplayName("1차 캐시 : comment 저장")
    @Rollback(value = false)
    void createCommentFail(){
        CommentRequestDto requestDto = new CommentRequestDto();
        requestDto.setComment("");

        service.createComment(requestDto,1L,request);
    }
    @Test
    @DisplayName("2차 캐시 : comment 변경")
    @Rollback(value = false)
    void updateCommentFail(){
        CommentRequestDto requestDto = new CommentRequestDto ();
        requestDto.setComment("2차 캐시 : comment 변경");
        service.updateComment(3100L,requestDto,request);
    }
    ///
    @Test
    @DisplayName("3차 캐시 : comment 삭제")
    @Rollback(value = false)
    void deleteCommentFail(){
        service.deleteComment(900L,request);
    }

    /////// 우저랑 feed 받아오기.
private User findById(long i) {
        return userrepo.findById(i).orElseThrow(()-> new NullPointerException("그런 유저 없음니다."));
}
    private Feed findByFeedBYId(long l) {
        return feedrepo.getById(l);
    }

}
