package com.sparta.duopleaseduo.service;

import com.sparta.duopleaseduo.dto.request.LoginRequestDto;
import com.sparta.duopleaseduo.dto.request.SignUpRequestDto;
import com.sparta.duopleaseduo.dto.response.UserResponseDto;
import com.sparta.duopleaseduo.entity.User;
import com.sparta.duopleaseduo.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired UserService userService;
    @Autowired UserRepository userRepository;

    private User user;
    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @BeforeEach
    void createUser() {
        user = userRepository.save(new User("test@email.com",
                passwordEncoder().encode("password"),
                "name","introduce"));
    }


    @DisplayName("회원가입")
    @Nested
    class signUp {
        @Test
        @DisplayName("회원가입 성공")
        void singUp_success() {
            //given
            SignUpRequestDto requestDto = new SignUpRequestDto();
            requestDto.setEmail("test2@email.com");
            requestDto.setPassword("password");
            requestDto.setUsername("testName");
            //when
            UserResponseDto userResponseDto = userService.signUp(requestDto);
            User findUser = userRepository.findByEmail(requestDto.getEmail()).orElseThrow();
            //then
            assertThat(userResponseDto.getId()).isEqualTo(findUser.getId());
        }

        @Test
        @DisplayName("중복이메일 실패")
        void singUp_fail_duplicateEmail() {
            //given
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

//    @DisplayName("로그인")
//    @Nested
//    class login {
//        @Test
//        @DisplayName("로그인 성공")
//        void login_success() {
//            //given
//            LoginRequestDto requestDto = new LoginRequestDto();
//            requestDto.setEmail("test@email.com");
//            requestDto.setPassword("password");
//            //when
//            UserResponseDto responseDto = userService.login(requestDto);
//            //then
//            assertThat(responseDto.getId()).isEqualTo(user.getId());
//        }
//
//        @Test
//        @DisplayName("로그인 실패_비밀번호 틀림")
//        void login_fail_wrongPassword() {
//            //given
//            LoginRequestDto requestDto = new LoginRequestDto();
//            requestDto.setEmail("test@email.com");
//            requestDto.setPassword("wrongPassword");
//            //when
//            //then
//            assertThrows(IllegalArgumentException.class,
//                    () -> userService.login(requestDto));
//        }
//    }
}