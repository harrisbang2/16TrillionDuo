package com.sparta.duopleaseduo.repository;

import com.sparta.duopleaseduo.entity.Feed;
import com.sparta.duopleaseduo.entity.FeedLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedLikeRepository extends JpaRepository<FeedLike, Long> {
    int countByFeed(Feed feed);
}
