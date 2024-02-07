package com.sparta.duopleaseduo.service;

import com.sparta.duopleaseduo.dto.FeedRequestDto;
import com.sparta.duopleaseduo.entity.Feed;
import com.sparta.duopleaseduo.entity.User;
import com.sparta.duopleaseduo.jwt.JwtUtil;
import com.sparta.duopleaseduo.repository.FeedRepository;
import com.sparta.duopleaseduo.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedService {
    private final FeedRepository feedRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;


    public Long createFeed(FeedRequestDto feedRequestDto, HttpServletRequest request) {
        String userName = jwtUtil.validateTokenAndGetUserName(request);

        //TODO : 유저 찾기
        User user = userRepository.findByUsername(userName).orElseThrow(()
        -> new IllegalStateException("해당 유저를 찾을 수 없습니다."));

        Feed feed = new Feed(user, feedRequestDto.getTitle(), feedRequestDto.getContents());

        feedRepository.save(feed);

        return feed.getId();
    }

}
