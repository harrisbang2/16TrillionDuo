package com.sparta.duopleaseduo.repository;
import com.sparta.duopleaseduo.entity.Comment;
import com.sparta.duopleaseduo.entity.CommentLike;
import com.sparta.duopleaseduo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    CommentLike findByUserAndComment(User user, Comment comment);
}
