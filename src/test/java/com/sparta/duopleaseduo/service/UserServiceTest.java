package com.sparta.duopleaseduo.service;

import com.sparta.duopleaseduo.dto.request.SignUpRequestDto;
import com.sparta.duopleaseduo.dto.response.UserResponseDto;
import com.sparta.duopleaseduo.entity.User;
import com.sparta.duopleaseduo.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired UserService userService;
    @Autowired UserRepository userRepository;


    @DisplayName("회원가입")
    @Nested
    class signUp {
        @Test
        @DisplayName("회원가입 성공")
        public void singUp_success() {
            //given
            SignUpRequestDto requestDto = new SignUpRequestDto();
            requestDto.setEmail("test@email.com");
            requestDto.setPassword("password");
            requestDto.setUsername("testName");
            //when
            UserResponseDto userResponseDto = userService.signUp(requestDto);
            User findUser = userRepository.findByEmail(requestDto.getEmail()).orElseThrow();
            //then
            Assertions.assertThat(userResponseDto.getId()).isEqualTo(findUser.getId());
        }

        @Test
        @DisplayName("중복이메일 실패")
        public void singUp_fail_duplicateEmail() {
            //given
            userRepository.save(new User("test@email.com","passoword","name","introduce"));

            SignUpRequestDto requestDto = new SignUpRequestDto();
            requestDto.setEmail("test@email.com");
            requestDto.setPassword("password");
            requestDto.setUsername("testName");
            //when
            //then
            assertThrows(IllegalArgumentException.class, () ->
                    userService.signUp(requestDto));
        }

    }
}