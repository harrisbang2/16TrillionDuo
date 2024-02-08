package com.sparta.duopleaseduo.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateUserRequestDto {

    private String password;
    private String username;
    private String introduce;
}
