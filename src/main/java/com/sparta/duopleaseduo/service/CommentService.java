package com.sparta.duopleaseduo.service;

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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final FeedRepository feedRepository;

    private CommentResponseDto commentResponseDto;
    private final
    JwtUtil jwtUtil;

    // 추가
    public CommentResponseDto createComment(CommentRequestDto requestDto, Long id, HttpServletRequest request) throws CommentCreateErrorException { /// 유저랑 Feed 추가할 예정
        Comment comment;
        Comment savecomment;
        try {
            // !!!!!!!!!!!!!!!!!! 테스트 를 위해 한 주석입니다.
            User user = userRepository.findByEmail(jwtUtil.validateTokenAndGetUserName(request)).orElseThrow(NoSuchUserException::new);
            Feed feed = feedRepository.findById(id).orElseThrow(() -> new NoSuchElementException("그런 feed 없어요"));
            //
            comment = new Comment(requestDto, user, feed); // user feed 추가 예정
            savecomment = commentRepository.save(comment);
        } catch (Exception e) {
            throw new CommentCreateErrorException();
        }
        // DB 저장
        // Entity -> ResponseDto
        commentResponseDto = new CommentResponseDto(savecomment);
        return commentResponseDto;
    }


    ///// 수정
    public CommentResponseDto updateComment(Long id, CommentRequestDto requestDto, HttpServletRequest request) throws CommentException, UserException {
        //  DB에 존재하는지 확인
        // 유저 확인.
        Comment comment;
        User user;
        comment = findComment(id);
        user = userRepository.findByEmail(jwtUtil.validateTokenAndGetUserName(request)).orElseThrow(NoSuchUserException::new);
        // 유저 검사
        // Feed 검사도 할가 했지만 뺏습니다 시간 지연을 최소한 으로 하기 위해서 그냥 comment id 랑 user id 로 했습니다
        if (!comment.getUser().getId().equals(user.getId())) {
            throw new IncorrectUserException();
        }
        // 업데이트
        try {
            comment.update(requestDto);
            commentRepository.save(comment);
        } catch (Exception e) {
            throw new CommentUpdateFailException();
        }
        commentResponseDto = new CommentResponseDto(comment);
        return commentResponseDto;
    }


    /// 삭제
    public CommentResponseDto deleteComment(Long id, HttpServletRequest request) throws CommentException, UserException { /// 유저랑 Feed 추가할 예정 (User user , Feed feed)
        Comment comment;
        User user;
        //
        comment = findComment(id);
        user = userRepository.findByEmail(jwtUtil.validateTokenAndGetUserName(request)).orElseThrow(NoSuchUserException::new);
        // 유저 확인.
        // Feed 검사도 할가 했지만 뺏습니다 시간 지연을 최소한 으로 하기 위해서 그냥 comment id 랑 user id 로 했습니다
        if (!comment.getUser().getId().equals(user.getId())) {
            throw new IllegalStateException("유저가 다릅니다!!!!");
        }
        // 삭제 진행
        try {
            commentRepository.delete(comment);
        } catch (Exception e) {
            System.out.println("deleteComment 에서 오류");
            return new CommentResponseDto();
        }
        return new CommentResponseDto(comment);
    }



    private Comment findComment(Long id) throws CommentException {
        return commentRepository.findById(id).orElseThrow(NoSuchCommentException::new);
    }


}
