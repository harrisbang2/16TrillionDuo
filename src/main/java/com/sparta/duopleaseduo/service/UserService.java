package com.sparta.duopleaseduo.service;

import com.sparta.duopleaseduo.dto.request.LoginRequestDto;
import com.sparta.duopleaseduo.dto.request.SignUpRequestDto;
import com.sparta.duopleaseduo.dto.request.UpdatePasswordRequestDto;
import com.sparta.duopleaseduo.dto.request.UpdateUserRequestDto;
import com.sparta.duopleaseduo.dto.response.UserResponseDto;
import com.sparta.duopleaseduo.entity.User;
import com.sparta.duopleaseduo.exception.userexception.IncorrectPasswordException;
import com.sparta.duopleaseduo.exception.userexception.NoSuchUserException;
import com.sparta.duopleaseduo.exception.userexception.UserAlreadyExistsException;
import com.sparta.duopleaseduo.jwt.JwtUtil;
import com.sparta.duopleaseduo.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.http.HttpResponse;
import java.nio.channels.NotYetBoundException;
import java.nio.file.AccessDeniedException;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Transactional
    public UserResponseDto signUp(SignUpRequestDto requestDto) {
        Optional<User> user = userRepository.findByEmail(requestDto.getEmail());
        if(user.isPresent()) {
            log.info("회원 이메일 중복");
            throw new UserAlreadyExistsException();
        }
        log.info("회원가입 정상 로직");
        User savedUser = userRepository.save(new User(requestDto, passwordEncoder().encode(requestDto.getPassword())));
        return new UserResponseDto(savedUser);
    }


    public UserResponseDto login(LoginRequestDto requestDto, HttpServletResponse response) {
        User findUser = userRepository.findByEmail(requestDto.getEmail()).orElseThrow(
                () -> new IncorrectPasswordException()
        );
        if(!passwordEncoder().matches(requestDto.getPassword(), findUser.getPassword())) {
            log.info("비밀번호 불일치");
            throw new IncorrectPasswordException();
        }
        log.info("로그인 성공");
        jwtUtil.setTokenInCookie(requestDto.getEmail(), response);
        return new UserResponseDto(findUser);
    }

    public UserResponseDto logout(HttpServletRequest request, HttpServletResponse response) {
        String userEmail = jwtUtil.validateTokenAndGetUserName(request);
        User findUser = userRepository.findByEmail(userEmail).orElseThrow(
                () -> new NoSuchUserException()
        );
        jwtUtil.expireToken(response);
        log.info("로그아웃 성공");
        return new UserResponseDto(findUser);
    }

    @Transactional
    public UserResponseDto updateUser(UpdateUserRequestDto requestDto, HttpServletRequest request) {
        String userEmail = jwtUtil.validateTokenAndGetUserName(request);
        User findUser = userRepository.findByEmail(userEmail).orElseThrow(
                () -> new NoSuchUserException()
        );
        if(!passwordEncoder().matches(requestDto.getPassword(), findUser.getPassword())) {
            throw new IncorrectPasswordException();
        };
        findUser.update(requestDto);
        return new UserResponseDto(findUser);
    }


    @Transactional
    public UserResponseDto updatePassword(UpdatePasswordRequestDto requestDto, HttpServletRequest request) {
        String userEmail = jwtUtil.validateTokenAndGetUserName(request);
        User findUser = userRepository.findByEmail(userEmail).orElseThrow(
                () -> new NoSuchUserException()
        );
        if (!passwordEncoder().matches(requestDto.getPassword(), findUser.getPassword())) {
            throw new IncorrectPasswordException();
        }
        findUser.updatePassword(requestDto);
        return new UserResponseDto(findUser);
    }


}
