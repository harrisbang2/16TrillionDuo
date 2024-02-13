package com.sparta.duopleaseduo.controller;


import com.sparta.duopleaseduo.dto.request.FeedFormDto;
import com.sparta.duopleaseduo.dto.response.FeedDetailResponseDto;
import com.sparta.duopleaseduo.dto.response.FeedListDto;
import com.sparta.duopleaseduo.dto.response.RiotUserResponseDto;
import com.sparta.duopleaseduo.dto.response.UserFeedListResponseDto;
import com.sparta.duopleaseduo.service.FeedService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feeds")
public class FeedController {
    private final FeedService feedService;
    private final String riotUri = "http://localhost:8080/riot/";

    @PostMapping
    public ResponseEntity<Long> createFeed(@RequestBody FeedFormDto feedFormDto, HttpServletRequest request) {
        RestTemplate restTemplate = new RestTemplate();
        RiotUserResponseDto riotUserInfo = restTemplate.getForObject(riotUri+feedFormDto.getSummonerName(), RiotUserResponseDto.class);

        Long feedId = feedService.createFeed(feedFormDto, riotUserInfo, request);

        return ResponseEntity.status(201).body(feedId);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Long> updateFeed(@PathVariable Long id, @RequestBody FeedFormDto feedFormDto, HttpServletRequest request) {
        Long feedId = feedService.updateFeed(id, feedFormDto, request);

        return ResponseEntity.ok(feedId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFeed(@PathVariable Long id, HttpServletRequest request) {
        feedService.deleteFeed(id, request);

        return ResponseEntity.ok("OK");
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserFeedListResponseDto> getUserFeedList(@PathVariable Long id, HttpServletRequest request) {
        UserFeedListResponseDto feedListDto = feedService.getUserFeedList(id, request);

        return ResponseEntity.ok(feedListDto);
    }

    @GetMapping
    public ResponseEntity<List<FeedListDto>> getMainFeedList() {
        List<FeedListDto> feedList = feedService.getMainFeedList();

        return ResponseEntity.ok(feedList);
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<FeedDetailResponseDto> getFeedDetail(@PathVariable Long id) {
        FeedDetailResponseDto feedDetailResponseDto = feedService.getFeedDetail(id);

        return ResponseEntity.ok(feedDetailResponseDto);
    }

    @PostMapping("/like/{id}")
    public ResponseEntity<String> likeFeed(@PathVariable Long id, HttpServletRequest request) {
        feedService.likeFeed(id, request);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/like/{id}")
    public ResponseEntity<String> cancelLikeFeed(@PathVariable Long id, HttpServletRequest request){
        feedService.cancelLikeFeed(id, request);

        return ResponseEntity.noContent().build();
    }
}
