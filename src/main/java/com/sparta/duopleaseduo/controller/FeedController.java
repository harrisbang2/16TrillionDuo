package com.sparta.duopleaseduo.controller;


import com.sparta.duopleaseduo.dto.FeedFormDto;
import com.sparta.duopleaseduo.dto.FeedListDto;
import com.sparta.duopleaseduo.dto.UserFeedListDto;
import com.sparta.duopleaseduo.service.FeedService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feed")
public class FeedController {
    private final FeedService feedService;


    @PostMapping("")
    public ResponseEntity<Long> createFeed(@RequestBody FeedFormDto feedFormDto, HttpServletRequest request) {
        Long feedId = feedService.createFeed(feedFormDto, request);

        return ResponseEntity.status(201).body(feedId);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Long> updateFeed(@PathVariable Long id, @RequestBody FeedFormDto feedFormDto, HttpServletRequest request){
        Long feedId = feedService.updateFeed(id, feedFormDto, request);

        return ResponseEntity.ok(feedId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFeed(@PathVariable Long id, HttpServletRequest request){
        feedService.deleteFeed(id, request);

        return ResponseEntity.ok("OK");
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserFeedListDto> getFeedList(@PathVariable Long id, HttpServletRequest request){
        UserFeedListDto feedListDto = feedService.getUserFeedList(id, request);

        return ResponseEntity.ok(feedListDto);
    }


    @GetMapping("")
    public ResponseEntity<List<FeedListDto>> getMainFeedList(){
        List<FeedListDto> feedList = feedService.getMainFeedList();

        return ResponseEntity.ok(feedList);
    }


}
