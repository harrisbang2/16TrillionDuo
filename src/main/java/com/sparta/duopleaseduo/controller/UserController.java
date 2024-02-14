package com.sparta.duopleaseduo.controller;

import com.sparta.duopleaseduo.dto.request.LoginRequestDto;
import com.sparta.duopleaseduo.dto.request.SignUpRequestDto;
import com.sparta.duopleaseduo.dto.request.UpdatePasswordRequestDto;
import com.sparta.duopleaseduo.dto.request.UpdateUserRequestDto;
import com.sparta.duopleaseduo.dto.response.UserResponseDto;
import com.sparta.duopleaseduo.jwt.JwtUtil;
import com.sparta.duopleaseduo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
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
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpRequestDto requestDto, 
                                    BindingResult bindingResult) {
        log.info("signUp Controller");
        ResponseEntity<List<String>> bindingResultList = checkBindingResult(bindingResult);
        if (bindingResultList != null) return bindingResultList;
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.signUp(requestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@RequestBody LoginRequestDto requestDto, 
                                                 HttpServletResponse response) {
        log.info("login Controller");
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.login(requestDto, response));
    }

    @PostMapping("/logout")
    public ResponseEntity<UserResponseDto> logout(HttpServletRequest request, 
                                                  HttpServletResponse response) {
        log.info("logout Controller");
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.logout(request, response));
    }

    @PatchMapping()
    public ResponseEntity<UserResponseDto> updateUser(@RequestBody UpdateUserRequestDto requestDto, 
                                                      HttpServletRequest request) {
        log.info("updateUser Controller");
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.updateUser(requestDto, request));
    }

    @PatchMapping("/password")
    public ResponseEntity<?> updatePassword(@Valid @RequestBody UpdatePasswordRequestDto requestDto,
                                                          HttpServletRequest request,
                                                          BindingResult bindingResult) {
        log.info("updatePassword Controller");
        ResponseEntity<List<String>> bindingResultList = checkBindingResult(bindingResult);
        if (bindingResultList != null) return bindingResultList;

        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.updatePassword(requestDto, request));
    }

    public ResponseEntity<List<String>> checkBindingResult(BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            log.info("입력 검증 오류 발생");
            return ResponseEntity.badRequest()
                    .body(createErrorMessages(bindingResult));
        }
        return null;
    }

    private List<String> createErrorMessages(BindingResult bindingResult) {
        return bindingResult.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
    }
}
