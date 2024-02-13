package com.sparta.duopleaseduo.repository;

import com.sparta.duopleaseduo.entity.Feed;
import com.sparta.duopleaseduo.entity.FeedLike;
import com.sparta.duopleaseduo.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FeedLikeRepositoryTest {

    @Autowired
    FeedLikeRepository feedLikeRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    FeedRepository feedRepository;



    @Test
    @DisplayName("좋아요 저장")
    @Rollback(value = false)
    void save(){
        User user = userRepository.findById(1L).orElseThrow();
        Feed feed = feedRepository.findById(3L).orElseThrow();

        if(feed.isUserMatch(user)){
            throw new IllegalStateException("중복 아이디가 있습니다.");
        }

        boolean feedLikeCheck = feedLikeRepository.existsByUserAndFeed(user, feed);
        System.out.println("데이터 : " + feedLikeCheck);

        feedLikeRepository.save(new FeedLike(user, feed));
    }

    @Test
    @DisplayName("좋아요 수 조회")
    @Rollback(value = false)
    void getFeedLike(){
        Feed feed = feedRepository.findById(2L).orElseThrow();

        int count = feedLikeRepository.countByFeed(feed);

        System.out.println(count);
        org.assertj.core.api.Assertions.assertThat(count).isEqualTo(3);
    }

}