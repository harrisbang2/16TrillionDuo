package com.sparta.duopleaseduo.controller;


import com.sparta.duopleaseduo.dto.CreateFeedDto;
import com.sparta.duopleaseduo.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feed")
public class FeedController {
    private final FeedService feedService;

//    @PostMapping("")
//    public ResponseEntity<Long> createFeed(@RequestBody CreateFeedDto createFeedDto, @RequestHeader("Authorization") String token) {
//        Long id = feedService.createFeed(createFeedDto, token);
//        return ResponseEntity.status(201).body(id);
//    }
}
