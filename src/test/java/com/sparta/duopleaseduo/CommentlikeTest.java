package com.sparta.duopleaseduo;

import com.sparta.duopleaseduo.entity.Comment;
import com.sparta.duopleaseduo.entity.CommentLike;
import com.sparta.duopleaseduo.entity.User;
import com.sparta.duopleaseduo.exception.commentexception.CommentException;
import com.sparta.duopleaseduo.exception.commentexception.CommentLikeExcecption;
import com.sparta.duopleaseduo.exception.commentexception.NoSuchCommentException;
import com.sparta.duopleaseduo.exception.commentexception.NoSuchLikedHistory;
import com.sparta.duopleaseduo.exception.userexception.NoSuchUserException;
import com.sparta.duopleaseduo.exception.userexception.UserException;
import com.sparta.duopleaseduo.exception.userexception.YesSuchException;
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

    @Test
    @DisplayName("1차 캐시 : comment like 저장")
    @Rollback(value = false)
    void Test1() throws UserException, CommentException {
        User user = userRepository.findById(1L).orElseThrow(NoSuchUserException::new);// 테스트
        Comment comment = commentRepository.findById(1L).orElseThrow(NoSuchCommentException::new);
        if(commentLikeRepository.existsByUserAndComment(user,comment)){
            throw new CommentLikeExcecption();
        }
        try{
            CommentLike commentLike = new CommentLike(user,comment);
            commentLikeRepository.save(commentLike);
        }catch (Exception e){
            throw new CommentLikeExcecption();
        }
        //return true;
    }
    @Test
    @DisplayName("2차 캐시 : comment like 제거")
    @Rollback(value = false)
    void Test2() throws UserException, CommentException {
        User user = userRepository.findById(1L).orElseThrow(NoSuchUserException::new);// 테스트
        Comment comment = commentRepository.findById(1L).orElseThrow(NoSuchCommentException::new);
        CommentLike commentLike;
        try{
            commentLike = commentLikeRepository.findByUserAndComment(user,comment);
        }catch (Exception e){
            throw new NoSuchLikedHistory();
        }
        commentLikeRepository.delete(commentLike);
        //return true;
    }
}
