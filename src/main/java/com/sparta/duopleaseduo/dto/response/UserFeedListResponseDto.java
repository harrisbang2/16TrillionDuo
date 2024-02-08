package com.sparta.duopleaseduo.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class UserFeedListResponseDto {
    private String username;
    private String introduce;
    private List<FeedListDto> feedList;


    public UserFeedListResponseDto(String username, String introduce, List<FeedListDto> feedList) {
        this.username = username;
        this.introduce = introduce;
        this.feedList = feedList;
    }
}
