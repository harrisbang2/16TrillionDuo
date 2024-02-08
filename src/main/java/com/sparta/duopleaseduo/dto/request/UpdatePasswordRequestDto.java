package com.sparta.duopleaseduo.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdatePasswordRequestDto {

    private String password;
    private String passwordConfirm;
}
