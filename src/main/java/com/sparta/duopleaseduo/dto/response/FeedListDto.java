package com.sparta.duopleaseduo.dto.response;

import com.sparta.duopleaseduo.entity.Feed;
import com.sparta.duopleaseduo.entity.RiotUser;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FeedListDto {

    private String username;
    private Long userId;
    @NotBlank
    private String title;
    @NotBlank
    private String contents;
    private RiotUser riotUser;

    public FeedListDto(Feed feed) {
        this.username = feed.getUser().getUsername();
        this.userId = feed.getUser().getId();
        this.title = feed.getTitle();
        this.contents = feed.getContent();
        this.riotUser = feed.getRiotUser();
    }
}
