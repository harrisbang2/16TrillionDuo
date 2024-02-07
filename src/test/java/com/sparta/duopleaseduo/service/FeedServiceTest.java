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
    void createFeed(){
        User user = new User("user@naver.com", "0000", "user", "user입니다");
        userRepository.save(user);

        Feed feed = new Feed(user, "title", "content");
        Feed save = feedRepository.save(feed);

        Assertions.assertEquals(feed, save);
    }

}