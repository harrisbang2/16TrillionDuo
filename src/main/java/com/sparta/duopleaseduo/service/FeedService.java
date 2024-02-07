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
        String email = jwtUtil.validateTokenAndGetUserName(request);

        User user = getUser(email);

        Feed feed = new Feed(user, feedRequestDto.getTitle(), feedRequestDto.getContents());

        feedRepository.save(feed);

        return feed.getId();
    }

    public Long updateFeed(Long id, FeedRequestDto feedRequestDto, HttpServletRequest request) {
        String email = jwtUtil.validateTokenAndGetUserName(request);
        User user = getUser(email);
        Feed feed = getFeed(id);

        validateUser(user, feed, "피드를 수정할 수 없습니다.");

        feed.update(feedRequestDto.getTitle(), feedRequestDto.getContents());
        return feed.getId();
    }


    public void deleteFeed(Long id, HttpServletRequest request) {
        String email = jwtUtil.validateTokenAndGetUserName(request);
        User user = getUser(email);
        Feed feed = getFeed(id);

        validateUser(user, feed, "피드를 삭제할 수 없습니다.");

        feedRepository.delete(feed);
    }

    private Feed getFeed(Long id) {
        return feedRepository.findById(id).orElseThrow(() -> new IllegalStateException("해당 피드를 찾지 못했습니다."));
    }

    private User getUser(String userName) {
        return userRepository.findByUsername(userName).orElseThrow(()
                -> new IllegalStateException("해당 유저를 찾을 수 없습니다."));
    }
    private void validateUser(User user, Feed feed, String s) {
        if (user != feed.getUser()) {
            throw new IllegalStateException(s);
        }
    }


}
