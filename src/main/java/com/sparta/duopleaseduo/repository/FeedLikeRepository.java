package com.sparta.duopleaseduo.repository;

import com.sparta.duopleaseduo.entity.Feed;
import com.sparta.duopleaseduo.entity.FeedLike;
import com.sparta.duopleaseduo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FeedLikeRepository extends JpaRepository<FeedLike, Long> {
    int countByFeed(Feed feed);
    boolean existsByUserAndFeed(User user, Feed feed);

    Optional<FeedLike> findByUserAndFeed(User user, Feed feed);
}
