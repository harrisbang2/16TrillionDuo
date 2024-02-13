package com.sparta.duopleaseduo;

import com.sparta.duopleaseduo.entity.Comment;
import com.sparta.duopleaseduo.entity.CommentLike;
import com.sparta.duopleaseduo.entity.User;
import com.sparta.duopleaseduo.repository.CommentLikeRepository;
import com.sparta.duopleaseduo.repository.CommentRepository;
import com.sparta.duopleaseduo.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.NoSuchElementException;

@SpringBootTest
public class CommentlikeTest {
    @Autowired
    private  CommentLikeRepository commentLikeRepository;
    @Autowired
    private  CommentRepository commentRepository;
    @Autowired
    private  UserRepository userRepository;
    HttpServletRequest request;

    @Test
    @DisplayName("1차 캐시 : comment like 저장")
    @Rollback(value = false)
    void Test1(){
        User user = userRepository.findById(1L).orElseThrow();// 테스트
        Comment comment = commentRepository.findById(1L).orElseThrow(()-> new NoSuchElementException("해당 댓글은 없습니다"));
        if(commentLikeRepository.existsByUserAndComment(user,comment)){
            throw new IllegalStateException("해당 댓글은 이미 좋아요 하셨습니다");
        }
        try{
            CommentLike commentLike = new CommentLike(user,comment);
            commentLikeRepository.save(commentLike);
        }catch (Exception e){
            throw new NullPointerException("댓글 좋아요 하는데 문제가 생김");
        }
        //return true;
    }
    @Test
    @DisplayName("2차 캐시 : comment like 제거")
    @Rollback(value = false)
    void Test2(){
        User user = userRepository.findById(1L).orElseThrow();// 테스트
        Comment comment = commentRepository.findById(1L).orElseThrow(()-> new NoSuchElementException("해당 댓글은 없습니다"));
        CommentLike commentLike;
        try{
            commentLike = commentLikeRepository.findByUserAndComment(user,comment);
        }catch (Exception e){
            throw new NoSuchElementException("해당 댓글에 like 한적이 없습니다");
        }
        commentLikeRepository.delete(commentLike);
        //return true;
    }
}
