package com.sparta.duopleaseduo.service;

import com.sparta.duopleaseduo.dto.FeedFormDto;
import com.sparta.duopleaseduo.dto.FeedListDto;
import com.sparta.duopleaseduo.dto.UserFeedListDto;
import com.sparta.duopleaseduo.entity.Feed;
import com.sparta.duopleaseduo.entity.User;
import com.sparta.duopleaseduo.jwt.JwtUtil;
import com.sparta.duopleaseduo.repository.FeedRepository;
import com.sparta.duopleaseduo.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedService {
    private final FeedRepository feedRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;


    public Long createFeed(FeedFormDto feedFormDto, HttpServletRequest request) {
        String email = jwtUtil.validateTokenAndGetUserName(request);

        User user = getUser(email);

        Feed feed = new Feed(user, feedFormDto.getTitle(), feedFormDto.getContents());

        feedRepository.save(feed);

        return feed.getId();
    }

    public Long updateFeed(Long id, FeedFormDto feedFormDto, HttpServletRequest request) {
        String email = jwtUtil.validateTokenAndGetUserName(request);
        User user = getUser(email);
        Feed feed = getFeed(id);

        validateUser(user, feed, "피드를 수정할 수 없습니다.");

        feed.update(feedFormDto.getTitle(), feedFormDto.getContents());
        return feed.getId();
    }


    public void deleteFeed(Long id, HttpServletRequest request) {
        String email = jwtUtil.validateTokenAndGetUserName(request);
        User user = getUser(email);
        Feed feed = getFeed(id);

        validateUser(user, feed, "피드를 삭제할 수 없습니다.");

        feedRepository.delete(feed);
    }

    public UserFeedListDto getUserFeedList(Long id, HttpServletRequest request) {
        String email = jwtUtil.validateTokenAndGetUserName(request);
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalStateException("해당 유저를 찾지 못했습니다."));
        List<FeedListDto> feedList = feedRepository.findAllByUser(user).stream().map(FeedListDto::new).toList();

        return new UserFeedListDto(user.getUsername(), user.getIntroduce(), feedList);
    }


    public List<FeedListDto> getMainFeedList() {
        return feedRepository.findAll().stream().map(FeedListDto::new).toList();
    }

    private Feed getFeed(Long id) {
        return feedRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("해당 피드를 찾지 못했습니다."));
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
