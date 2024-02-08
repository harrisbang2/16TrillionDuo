package com.sparta.duopleaseduo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FeedFormDto {
    @NotBlank
    private String title;
    @NotBlank
    private String contents;
}
