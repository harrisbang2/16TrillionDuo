package com.sparta.duopleaseduo.dto.request;

import com.sparta.duopleaseduo.entity.Feed;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FeedFormDto {
    @NotBlank(message = "제목을 적어주세요.")
    private String title;
    @NotBlank(message = "내용을 적어주세요.")
    private String contents;
    @NotBlank(message = "소환사명을 적어주세요.")
    private String summonerName;

    public FeedFormDto(Feed feed) {
        this.title = feed.getTitle();
        this.contents = feed.getContent();
    }
}
