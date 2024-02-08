package com.sparta.duopleaseduo.dto;

import com.sparta.duopleaseduo.entity.Feed;
import lombok.Data;

@Data
public class FeedListDto {
    private String title;
    private String contents;

    public FeedListDto(Feed feed) {
        this.title = feed.getTitle();
        this.contents = feed.getContent();
    }
}
