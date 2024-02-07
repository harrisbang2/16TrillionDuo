package com.sparta.duopleaseduo.service;

import com.sparta.duopleaseduo.entity.Feed;
import com.sparta.duopleaseduo.entity.User;
import com.sparta.duopleaseduo.repository.FeedRepository;
import com.sparta.duopleaseduo.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class FeedServiceTest {

    @Autowired
    FeedRepository feedRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("피드 추가")
    @Rollback(value = false)
    void createFeed(){
        User user = new User("user@naver.com", "0000", "user", "user입니다");
        userRepository.save(user);

        Feed feed = new Feed(user, "title", "content");
        Feed save = feedRepository.save(feed);

        Assertions.assertEquals(feed, save);
    }

    @Test
    @DisplayName("피드 수정")
    @Rollback(value = false)
    void updateFeed(){
        User user = userRepository.findById(1L).orElseThrow(()
        -> new IllegalStateException("해당 유저를 찾을 수 없습니다.")
        );

        Feed feed = feedRepository.findById(1L).orElseThrow(()
                -> new IllegalStateException("해당 피드를 찾을 수 없습니다.")
        );

        if(user != feed.getUser()){
            throw new IllegalStateException("해당 피드를 수정할 수 없습니다.");
        }

        feed.update("title2", "content2");
    }

}