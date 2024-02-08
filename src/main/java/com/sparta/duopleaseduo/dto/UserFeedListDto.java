package com.sparta.duopleaseduo.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserFeedListDto {
    private String username;
    private String introduce;
    private List<FeedListDto> feedList;


    public UserFeedListDto(String username, String introduce, List<FeedListDto> feedList) {
        this.username = username;
        this.introduce = introduce;
        this.feedList = feedList;
    }
}
