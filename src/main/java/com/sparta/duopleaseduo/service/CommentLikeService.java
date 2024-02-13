package com.sparta.duopleaseduo.service;

import com.sparta.duopleaseduo.entity.Comment;
import com.sparta.duopleaseduo.entity.CommentLike;
import com.sparta.duopleaseduo.entity.User;
import com.sparta.duopleaseduo.jwt.JwtUtil;
import com.sparta.duopleaseduo.repository.CommentLikeRepository;
import com.sparta.duopleaseduo.repository.CommentRepository;
import com.sparta.duopleaseduo.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CommentLikeService {
    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;



    public boolean createComment(Long commentId, HttpServletRequest request) {
         String username =jwtUtil.validateTokenAndGetUserName(request);
         User user = userRepository.findByUsername(username).orElseThrow(()-> new NoSuchElementException("해당 유저는 없습니다"));

         Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new NoSuchElementException("해당 댓글은 없습니다"));
         if(commentLikeRepository.existsByUserAndComment(user,comment)){
             throw new IllegalStateException("해당 댓글은 이미 좋아요 하셨습니다");
         }
         try{
             CommentLike commentLike = new CommentLike(user,comment);
             commentLikeRepository.save(commentLike);
         }catch (Exception e){
             throw new NullPointerException("댓글 좋아요 하는데 문제가 생김");
         }
         return true;
    }


    public boolean deleteComment(Long commentId, HttpServletRequest request) {
        String username =jwtUtil.validateTokenAndGetUserName(request);
        User user = userRepository.findByUsername(username).orElseThrow(()-> new NoSuchElementException("해당 유저는 없습니다"));

        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new NoSuchElementException("해당 댓글은 없습니다"));
        CommentLike commentLike;
        try{
            commentLike = commentLikeRepository.findByUserAndComment(user,comment);
        }catch (Exception e){
            throw new NoSuchElementException("해당 댓글에 like 한적이 없습니다");
        }
        commentLikeRepository.delete(commentLike);
        return true;
    }
}
