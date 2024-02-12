package com.sparta.duopleaseduo.service;

import com.sparta.duopleaseduo.dto.request.FeedFormDto;
import com.sparta.duopleaseduo.dto.response.CommentResponseDto;
import com.sparta.duopleaseduo.dto.response.FeedDetailResponseDto;
import com.sparta.duopleaseduo.dto.response.FeedListDto;
import com.sparta.duopleaseduo.dto.response.UserFeedListResponseDto;
import com.sparta.duopleaseduo.entity.Feed;
import com.sparta.duopleaseduo.entity.FeedLike;
import com.sparta.duopleaseduo.entity.User;
import com.sparta.duopleaseduo.jwt.JwtUtil;
import com.sparta.duopleaseduo.repository.CommentRepository;
import com.sparta.duopleaseduo.repository.FeedLikeRepository;
import com.sparta.duopleaseduo.repository.FeedRepository;
import com.sparta.duopleaseduo.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FeedService {
    private final FeedRepository feedRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final FeedLikeRepository feedLikeRepository;
    private final JwtUtil jwtUtil;

    @Transactional(readOnly = true)
    public UserFeedListResponseDto getUserFeedList(Long id, HttpServletRequest request) {
        String email = jwtUtil.validateTokenAndGetUserName(request);
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("해당 유저를 찾지 못했습니다."));

        List<FeedListDto> feedList = feedRepository.findAllByUser(user)
                .stream()
                .map(FeedListDto::new)
                .toList();

        return new UserFeedListResponseDto(user.getUsername(), user.getIntroduce(), feedList);
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

        return new FeedDetailResponseDto(
                feed.getUser().getUsername(),
                feed.getTitle(),
                feed.getContent(),
                count,
                feed.getCreateAt(),
                comments
        );
    }

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

    public void likeFeed(Long id, HttpServletRequest request) {
        String email = jwtUtil.validateTokenAndGetUserName(request);
        User user = getUser(email);
        Feed feed = getFeed(id);

        validateLike(feed, user);
        //
        feedLikeRepository.save(new FeedLike(user, feed));
    }


    public void cancelLikeFeed(Long id, HttpServletRequest request) {
        String email = jwtUtil.validateTokenAndGetUserName(request);
        User user = getUser(email);
        Feed feed = getFeed(id);

        FeedLike feedLike = feedLikeRepository.findByUserAndFeed(user, feed).orElseThrow(
                () -> new IllegalStateException("해당 글에 좋아요를 누르지 않았습니다.")
        );

        feedLikeRepository.delete(feedLike);
    }

    private Feed getFeed(Long id) {
        return feedRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("해당 피드를 찾지 못했습니다."));
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email).orElseThrow(()
                -> new IllegalStateException("해당 유저를 찾을 수 없습니다."));
    }

    private void validateUser(User user, Feed feed, String message) {
        if (user != feed.getUser()) {
            throw new IllegalStateException(message);
        }
    }

    private void validateLike(Feed feed, User user) {
        if (feed.isUserMatch(user)) {
            throw new IllegalStateException("본인이 쓴 글에는 좋아요를 누를 수 없습니다.");
        }

        if(feedLikeRepository.existsByUserAndFeed(user, feed)){
            throw new IllegalStateException("좋아요는 한 번만 누를 수 있습니다.");
        }
    }

}
