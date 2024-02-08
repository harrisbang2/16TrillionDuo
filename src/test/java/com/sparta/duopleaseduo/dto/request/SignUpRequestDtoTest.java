package com.sparta.duopleaseduo.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class SignUpRequestDtoTest {

    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = factory.getValidator();

    @DisplayName("회원가입 DTO 생성")
    @Nested
    class createSignUpDto {

        @DisplayName("회원가입 DTO생성 성공")
        @Test
        void createSignUpRequestDto_success() {
            SignUpRequestDto requestDto = new SignUpRequestDto();
            requestDto.setEmail("test@eamil.com");
            requestDto.setPassword("password");
            requestDto.setUsername("test");

            Set<ConstraintViolation<SignUpRequestDto>> violations = validator.validate(requestDto);
            assertThat(violations).isEmpty();
        }

        @DisplayName("회원가입 DTO생성 실패_잘못된 이메일 형식")
        @Test
        void createSignUpRequestDto_wrongEmail() {
            SignUpRequestDto requestDto = new SignUpRequestDto();
            requestDto.setEmail("testemail");
            requestDto.setPassword("password");
            requestDto.setUsername("test");

            Set<ConstraintViolation<SignUpRequestDto>> violations = validator.validate(requestDto);

            assertThat(violations).hasSize(1)
                    .extracting("message")
                    .contains("이메일 형식으로 입력해야 합니다.");
        }

        @DisplayName("회원가입 DTO생성 실패_이메일 공백")
        @Test
        void createSignUpRequestDto_blankEmail() {
            SignUpRequestDto requestDto = new SignUpRequestDto();
            requestDto.setPassword("password");
            requestDto.setUsername("test");

            Set<ConstraintViolation<SignUpRequestDto>> violations = validator.validate(requestDto);

            assertThat(violations).hasSize(1)
                    .extracting("message")
                    .contains("이메일은 필수 입력 값 입니다.");
        }

        @DisplayName("회원가입 DTO생성 실패_잘못된 비밀번호 형식")
        @Test
        void createSignUpRequestDto_wrongPassword() {
            SignUpRequestDto requestDto = new SignUpRequestDto();
            requestDto.setEmail("test@eamil.com");
            requestDto.setPassword("password--");
            requestDto.setUsername("test");

            Set<ConstraintViolation<SignUpRequestDto>> violations = validator.validate(requestDto);

            assertThat(violations).hasSize(1)
                    .extracting("message")
                    .contains("비밀번호는 알파벳 대,소문자와 숫자로만 구성되야 합니다.");
        }

        @DisplayName("회원가입 DTO생성 실패_잘못된 비밀번호 길이")
        @Test
        void createSignUpRequestDto_wrongSizePassword() {
            SignUpRequestDto requestDto = new SignUpRequestDto();
            requestDto.setEmail("test@eamil.com");
            requestDto.setPassword("pass");
            requestDto.setUsername("test");

            Set<ConstraintViolation<SignUpRequestDto>> violations = validator.validate(requestDto);

            assertThat(violations).hasSize(1)
                    .extracting("message")
                    .contains("비밀번호는 7자 이상 15자 이하 이어야 합니다.");
        }
    }
}