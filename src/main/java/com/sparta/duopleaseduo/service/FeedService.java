package com.sparta.duopleaseduo.service;

import com.sparta.duopleaseduo.dto.request.FeedFormDto;
import com.sparta.duopleaseduo.dto.response.*;
import com.sparta.duopleaseduo.entity.Feed;
import com.sparta.duopleaseduo.entity.FeedLike;
import com.sparta.duopleaseduo.entity.RiotUser;
import com.sparta.duopleaseduo.entity.User;
import com.sparta.duopleaseduo.exception.feed.AlreadyLikedException;
import com.sparta.duopleaseduo.exception.feed.UserNotMatchException;
import com.sparta.duopleaseduo.jwt.JwtUtil;
import com.sparta.duopleaseduo.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class FeedService {
    private final FeedRepository feedRepository;
    private final UserRepository userRepository;
    private final RiotUserRepository riotUserRepository;
    private final CommentRepository commentRepository;
    private final FeedLikeRepository feedLikeRepository;
    private final JwtUtil jwtUtil;

    @Transactional(readOnly = true)
    public UserFeedListResponseDto getUserFeedList(Long id, HttpServletRequest request) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("해당 유저를 찾지 못했습니다."));

        List<FeedListDto> feedList = feedRepository.findAllByUser(user)
                .stream()
                .map(FeedListDto::new)
                .toList();
        ;
        return new UserFeedListResponseDto(user, feedList);
    }

    @Transactional(readOnly = true)
    public List<FeedListDto> getMainFeedList() {
        return feedRepository.findAll().stream().map(FeedListDto::new).toList();
    }

    @Transactional(readOnly = true)
    public FeedDetailResponseDto getFeedDetail(Long id) {
        Feed feed = getFeed(id);
        List<CommentResponseDto> comments = commentRepository.findAllByFeed(feed)
                .stream()
                .map(CommentResponseDto::new).toList();
        int count = feedLikeRepository.countByFeed(feed);

        return new FeedDetailResponseDto(feed, count, comments);
    }

    public Long createFeed(FeedFormDto feedFormDto, RiotUserResponseDto riotUserInfo, HttpServletRequest request) {
        String email = jwtUtil.validateTokenAndGetUserName(request);
        RiotUser savedRiotUser = riotUserRepository.save(new RiotUser(riotUserInfo));
        User user = getUser(email);
        Feed feed = new Feed(user, feedFormDto.getTitle(), feedFormDto.getContents(), savedRiotUser);

        log.info("글작성 성공");
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

        log.info("삭제 성공");
        feedRepository.delete(feed);
    }

    public void likeFeed(Long id, HttpServletRequest request) {
        String email = jwtUtil.validateTokenAndGetUserName(request);
        User user = getUser(email);
        Feed feed = getFeed(id);

        validateUser(user, feed, "해당 글에는 좋아요를 누를 수 없습니다.");
        validateLike(user, feed);
        //
        feedLikeRepository.save(new FeedLike(user, feed));
    }


    public void cancelLikeFeed(Long id, HttpServletRequest request) {
        String email = jwtUtil.validateTokenAndGetUserName(request);
        User user = getUser(email);
        Feed feed = getFeed(id);

        FeedLike feedLike = feedLikeRepository.findByUserAndFeed(user, feed).orElseThrow(
                () -> new EntityNotFoundException("해당 글에 좋아요를 누르지 않았습니다.")
        );

        feedLikeRepository.delete(feedLike);
    }

    private Feed getFeed(Long id) {
        return feedRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("해당 피드를 찾지 못했습니다."));
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email).orElseThrow(()
                -> new EntityNotFoundException("해당 유저를 찾지 못했습니다."));

    }

    private void validateUser(User user, Feed feed, String message) {
        if (feed.isUserMatch(user)) {
            log.info(message);
            throw new UserNotMatchException(message);
        }
    }

    private void validateLike(User user, Feed feed) {
        if (feedLikeRepository.existsByUserAndFeed(user, feed)) {
            log.info("좋아요는 한 번만 가능합니다.");
            throw new AlreadyLikedException("좋아요는 한 번만 누를 수 있습니다.");
        }
    }

}
