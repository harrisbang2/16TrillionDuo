package com.sparta.duopleaseduo.controller;

import com.sparta.duopleaseduo.dto.request.LoginRequestDto;
import com.sparta.duopleaseduo.dto.request.SignUpRequestDto;
import com.sparta.duopleaseduo.dto.request.UpdatePasswordRequestDto;
import com.sparta.duopleaseduo.dto.request.UpdateUserRequestDto;
import com.sparta.duopleaseduo.dto.response.UserResponseDto;
import com.sparta.duopleaseduo.jwt.JwtUtil;
import com.sparta.duopleaseduo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users")
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

    @PatchMapping()
    public ResponseEntity<UserResponseDto> updateUser(@RequestBody UpdateUserRequestDto requestDto, HttpServletRequest request) {
        log.info("updateUser Controller");
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.updateUser(requestDto, request));
    }

    @PatchMapping("/password")
    public ResponseEntity<UserResponseDto> updatePassword(@RequestBody UpdatePasswordRequestDto requestDto, HttpServletRequest request) {
        log.info("updatePassword Controller");
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.updatePassword(requestDto, request));
    }

    private List<String> createErrorMessages(BindingResult bindingResult) {
        return bindingResult.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
    }
}
