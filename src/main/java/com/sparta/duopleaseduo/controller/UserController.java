package com.sparta.duopleaseduo.controller;

import com.sparta.duopleaseduo.dto.request.LoginRequestDto;
import com.sparta.duopleaseduo.dto.request.SignUpRequestDto;
import com.sparta.duopleaseduo.dto.response.UserResponseDto;
import com.sparta.duopleaseduo.jwt.JwtUtil;
import com.sparta.duopleaseduo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpRequestDto requestDto, BindingResult bindingResult) {
        log.info("signUp Controller");
        if(bindingResult.hasErrors()) {
            log.info("회원가입 입력 검증오류 발생");
            return ResponseEntity.badRequest()
                    .body(createErrorMessages(bindingResult));
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.signUp(requestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@RequestBody LoginRequestDto requestDto) {
        log.info("login Controller");
        UserResponseDto responseDto = userService.login(requestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.AUTHORIZATION, jwtUtil.createToken(requestDto.getEmail()))
                .body(responseDto);
    }



    private List<String> createErrorMessages(BindingResult bindingResult) {
        return bindingResult.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
    }
}
