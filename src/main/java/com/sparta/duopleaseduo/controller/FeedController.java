package com.sparta.duopleaseduo.controller;


import com.sparta.duopleaseduo.dto.FeedRequestDto;
import com.sparta.duopleaseduo.service.FeedService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feed")
public class FeedController {
    private final FeedService feedService;


    @PostMapping("")
    public ResponseEntity<Long> createFeed(@RequestBody FeedRequestDto feedRequestDto, HttpServletRequest request) {
        Long id = feedService.createFeed(feedRequestDto, request);

        return ResponseEntity.status(201).body(id);
    }
}
