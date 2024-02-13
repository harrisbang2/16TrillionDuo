package com.sparta.duopleaseduo.dto.response;

import com.sparta.duopleaseduo.dto.request.FeedFormDto;
import lombok.Data;

import java.util.List;

@Data
public class UserFeedResponseDto {
    private String username;
    private String introduce;
    private List<FeedFormDto> feedList;


    public UserFeedResponseDto(String username, String introduce, List<FeedFormDto> feedList) {
        this.username = username;
        this.introduce = introduce;
        this.feedList = feedList;
    }
}
