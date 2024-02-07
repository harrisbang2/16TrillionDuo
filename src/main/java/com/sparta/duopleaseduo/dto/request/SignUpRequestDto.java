package com.sparta.duopleaseduo.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SignUpRequestDto {

    @NotBlank(message = "이메일은 필수 입력 값 입니다.")
    @Email(message = "이메일 형식으로 입력해야 합니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 값 입니다.")
    @Size(min = 7, max = 15, message = "비밀번호는 7자 이상 15자 이하 이어야 합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "비밀번호는 알파벳 대,소문자와 숫자로만 구성되야 합니다.")
    private String password;

    @NotBlank(message = "이름은 필수 입력 값 입니다.")
    private String username;

    private String introduce;
}
