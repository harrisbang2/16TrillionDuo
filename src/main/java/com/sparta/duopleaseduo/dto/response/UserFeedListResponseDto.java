package com.sparta.duopleaseduo.dto.response;

import com.sparta.duopleaseduo.entity.User;
import lombok.Data;

import java.util.List;

@Data
public class UserFeedListResponseDto {
    private Long userId;
    private String username;
    private String introduce;
    private List<FeedListDto> feedList;


    public UserFeedListResponseDto(User user, List<FeedListDto> feedList) {
        this.userId = user.getId();
        this.username = user.getUsername();
        this.introduce = user.getIntroduce();
        this.feedList = feedList;
    }
}
