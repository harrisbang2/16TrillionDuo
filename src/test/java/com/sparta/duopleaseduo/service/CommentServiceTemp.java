package com.sparta.duopleaseduo.service;

import com.sparta.duopleaseduo.dto.request.CommentRequestDto;
import com.sparta.duopleaseduo.dto.response.CommentResponseDto;
import com.sparta.duopleaseduo.entity.Comment;
import com.sparta.duopleaseduo.entity.Feed;
import com.sparta.duopleaseduo.entity.User;
import com.sparta.duopleaseduo.jwt.JwtUtil;
import com.sparta.duopleaseduo.repository.CommentRepository;
import com.sparta.duopleaseduo.repository.FeedRepository;
import com.sparta.duopleaseduo.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
    @Service
    public class CommentServiceTemp {
        @Autowired
        private  CommentRepository commentRepository;
        @Autowired
        private  UserRepository userRepository;
        @Autowired
        private  FeedRepository feedRepository;

        private CommentResponseDto commentResponseDto;

        private JwtUtil jwtUtil;

        // 추가
        public CommentResponseDto createComment(CommentRequestDto requestDto, Long id, HttpServletRequest request) { /// 유저랑 Feed 추가할 예정
            Comment comment;
            Comment savecomment;
            //System.out.println(jwtUtil.validateTokenAndGetUserName(request));
            try {
                // !!!!!!!!!!!!!!!!!! 테스트 를 위해 한 주석입니다.
                User user = userRepository.findById(1L).orElseThrow(()->new NoSuchElementException("그런 유저 없어요"));
                Feed feed = feedRepository.findById(id).orElseThrow(()->new NoSuchElementException("그런 feed 없어요"));
                //
                comment = new Comment(requestDto, user, feed); // user feed 추가 예정
                savecomment = commentRepository.save(comment);
            }catch (Exception e){
                throw new RuntimeException("저장에 오류가 생겼습니다.");
            }
            // DB 저장
            // Entity -> ResponseDto
            commentResponseDto = new CommentResponseDto(savecomment);
            return commentResponseDto;
        }


        ///// 수정
        public CommentResponseDto updateComment(Long id, CommentRequestDto requestDto, HttpServletRequest request) {  /// 유저랑 Feed 추가할 예정 (User user , Feed feed)
            //  DB에 존재하는지 확인
            // 유저 확인.
            Comment comment;
            User user;
            try {
                comment = findComment(id);
                // !!!!!!!!!!!!!!!!!! 테스트 를 위해 한 주석입니다. !!!!!!!!!!
                //user = userRepository.findByEmail(jwtUtil.validateTokenAndGetUserName(request)).orElseThrow(() -> new NoSuchElementException("회원이 아닙니다."));
                user = userRepository.findById(1L).orElseThrow(()->new NoSuchElementException("그런 유저 없어요"));
            }catch (Exception e){
                throw new NoSuchElementException("updateComment 에 없는 Comment 아이디 입니다");
            }
            // 유저 검사
            // Feed 검사도 할가 했지만 뺏습니다 시간 지연을 최소한 으로 하기 위해서 그냥 comment id 랑 user id 로 했습니다
            if(!comment.getUser().getId().equals(user.getId())){
                throw new IllegalStateException("유저가 다릅니다!!!!");
            }
            // 업데이트
            try{
                comment.update(requestDto);
                commentRepository.save(comment);
            } catch (Exception e){
                throw new RuntimeException("updateComment 에서 오류");
            }
            commentResponseDto=new CommentResponseDto(comment);
            return commentResponseDto;
        }

        /// 삭제
        public void deleteComment(Long id, HttpServletRequest request) { /// 유저랑 Feed 추가할 예정 (User user , Feed feed)
            Comment comment;
            User user;
            //
            try {
                comment = findComment(id);
                user = userRepository.findById(1L).orElseThrow(() -> new NoSuchElementException("deleteComment 유저 검색 실패"));
            }catch (Exception e){
                throw new NoSuchElementException("deleteComment 에 없는 Comment 입니다");
            }
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
                new CommentResponseDto();
                return;
            }
            new CommentResponseDto(comment);
        }
        private Comment findComment(Long id) {
            return commentRepository.findById(id).orElseThrow(()-> new NullPointerException("findComment 유저 검색 실패"));
        }
    }

