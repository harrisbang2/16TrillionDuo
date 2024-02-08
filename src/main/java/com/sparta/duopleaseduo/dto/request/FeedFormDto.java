package com.sparta.duopleaseduo.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FeedFormDto {
    @NotBlank(message = "제목을 적어주세요.")
    private String title;
    @NotBlank(message = "내용을 적어주세요.")
    private String contents;
}
