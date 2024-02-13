package com.sparta.duopleaseduo.dto.request;

import com.sparta.duopleaseduo.entity.Feed;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FeedFormDto {
    @NotBlank(message = "제목을 적어주세요.")
    private String title;
    @NotBlank(message = "내용을 적어주세요.")
    private String contents;


    public FeedFormDto(Feed feed) {
        this.title = feed.getTitle();
        this.contents = feed.getContent();
    }
}
