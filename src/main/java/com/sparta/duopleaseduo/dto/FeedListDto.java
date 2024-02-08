package com.sparta.duopleaseduo.dto;

import com.sparta.duopleaseduo.entity.Feed;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FeedListDto {
    @NotBlank
    private String title;
    @NotBlank
    private String contents;

    public FeedListDto(Feed feed) {
        this.title = feed.getTitle();
        this.contents = feed.getContent();
    }
}
