package com.sparta.duopleaseduo;


import com.sparta.duopleaseduo.dto.request.CommentRequestDto;
import com.sparta.duopleaseduo.dto.response.CommentResponseDto;
import com.sparta.duopleaseduo.entity.Comment;
import com.sparta.duopleaseduo.entity.Feed;
import com.sparta.duopleaseduo.entity.User;
import com.sparta.duopleaseduo.exception.commentexception.*;
import com.sparta.duopleaseduo.exception.userexception.NoSuchUserException;
import com.sparta.duopleaseduo.exception.userexception.UserException;
import com.sparta.duopleaseduo.jwt.JwtUtil;
import com.sparta.duopleaseduo.repository.CommentRepository;
import com.sparta.duopleaseduo.repository.FeedRepository;
import com.sparta.duopleaseduo.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.NoSuchElementException;

@SpringBootTest
public class CommentTest {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FeedRepository feedRepository;

    private CommentResponseDto commentResponseDto;

    Comment comment;
    Comment savecomment;

    // 추가
    @Test
    @DisplayName("1차 캐시 : comment 저장")
    @Rollback(value = false)
    void test1() throws CommentException {
        CommentRequestDto requestDto = new CommentRequestDto ();
        requestDto.setComment("1차 캐시 : comment 저장");
        try {
            User user = userRepository.findById(1L).orElseThrow(()->new NoSuchElementException("그런 유저 없어요"));
            Feed feed = feedRepository.findById(1L).orElseThrow(()->new NoSuchElementException("그런 feed 없어요"));
            //
            comment = new Comment(requestDto, user, feed); // user feed 추가 예정
            savecomment = commentRepository.save(comment);
        }catch (Exception e){
            throw new CommentCreateErrorException();
        }
        // DB 저장
        // Entity -> ResponseDto
        commentResponseDto = new CommentResponseDto(savecomment);
    }


    @Test
    @DisplayName("2차 캐시 : comment 변경")
    @Rollback(value = false)
    void test2() throws NoSuchUserException, CommentException {
        CommentRequestDto requestDto = new CommentRequestDto ();
        requestDto.setComment("2차 캐시 : comment 변경");
        //  DB에 존재하는지 확인
        // 유저 확인.
        Comment comment;
        User user;
        comment = findComment(4L);
        // !!!!!!!!!!!!!!!!!! 테스트 를 위해 한 주석입니다. !!!!!!!!!!
        //user = userRepository.findByEmail(jwtUtil.validateTokenAndGetUserName(request)).orElseThrow(() -> new NoSuchElementException("회원이 아닙니다."));
        user = userRepository.findById(1L).orElseThrow(NoSuchUserException::new);
        // 유저 검사
        // Feed 검사도 할가 했지만 뺏습니다 시간 지연을 최소한 으로 하기 위해서 그냥 comment id 랑 user id 로 했습니다
        if(!comment.getUser().getId().equals(user.getId())){
            throw new IncorrectUserException();
        }
        // 업데이트
        try{
            comment.update(requestDto);
            commentRepository.save(comment);
        } catch (Exception e){
            throw new CommentUpdateFailException();
        }
    }

    @Test
    @DisplayName("3차 캐시 : comment 삭제")
    @Rollback(value = false)
   void test3() throws CommentException, NoSuchUserException {
        Comment comment;
        User user;
        //
        comment = findComment(4L);
        user = userRepository.findById(1L).orElseThrow(NoSuchUserException::new);
        // 유저 확인.
        // Feed 검사도 할가 했지만 뺏습니다 시간 지연을 최소한 으로 하기 위해서 그냥 comment id 랑 user id 로 했습니다
        if(!comment.getUser().getId().equals(user.getId())){
            throw new IllegalStateException("유저가 다릅니다!!!!");
        }
        // 삭제 진행
        try{
            commentRepository.delete(comment);
        } catch (Exception e){
            System.out.println("deleteComment 에서 오류");
        }
    }

    private Comment findComment(Long id) throws CommentException {
        return commentRepository.findById(id).orElseThrow(NoSuchCommentException::new);
    }
}
