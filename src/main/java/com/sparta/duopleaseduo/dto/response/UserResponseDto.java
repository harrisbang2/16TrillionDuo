package com.sparta.duopleaseduo.dto.response;

import com.sparta.duopleaseduo.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserResponseDto {

    private Long id;
    public UserResponseDto(User savedUser) {
        this.id = savedUser.getId();
    }
}
