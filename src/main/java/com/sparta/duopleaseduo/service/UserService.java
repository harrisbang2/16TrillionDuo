package com.sparta.duopleaseduo.service;

import com.sparta.duopleaseduo.dto.request.SignUpRequestDto;
import com.sparta.duopleaseduo.dto.response.UserResponseDto;
import com.sparta.duopleaseduo.entity.User;
import com.sparta.duopleaseduo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Transactional
    public UserResponseDto signUp(SignUpRequestDto requestDto) {
        Optional<User> user = userRepository.findByEmail(requestDto.getEmail());
        if(user.isPresent()) {
            log.info("회원 이메일 중복");
            throw new IllegalArgumentException("이미 존재하는 email 입니다.");
        }
        log.info("회원가입 정상 로직");
        User savedUser = userRepository.save(new User(requestDto, passwordEncoder().encode(requestDto.getPassword())));
        return new UserResponseDto(savedUser);
    }
}
