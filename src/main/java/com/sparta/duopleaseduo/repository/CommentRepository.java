package com.sparta.duopleaseduo.repository;

import com.sparta.duopleaseduo.entity.Comment;
import com.sparta.duopleaseduo.entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByFeed(Feed feed);
}
