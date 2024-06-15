package com.sparta.newspeed.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("SignupReqDto 테스트")
public class SignupReqDtoTest {


    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }


    @Test
    @DisplayName("모든 필드가 유효할 때")
    public void whenAllFieldsAreValid() {
        SignupReqDto signupReqDto = new SignupReqDto("validNickname", "validPassword", "John Doe",
                                                "john.doe@example.com", "Hello!");

        Set<ConstraintViolation<SignupReqDto>> violations = validator.validate(signupReqDto);
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("닉네임이 빈 경우")
    public void whenNicknameIsBlank() {
        SignupReqDto signupReqDto = new SignupReqDto("", "validPassword", "John Doe",
                "john.doe@example.com", "Hello!");

        Set<ConstraintViolation<SignupReqDto>> violations = validator.validate(signupReqDto);
        assertThat(violations).hasSize(3);

        List<String> expectedMessages = Arrays.asList(
                "nickname은 필수입니다.",
                "nickname은 최소 10자 이상, 20자 이하여야합니다.",
                "nickname은 알파벳 대소문자, 숫자로 구성되야합니다."
        );
        List<String> actualMessages = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        assertThat(actualMessages).containsExactlyInAnyOrderElementsOf(expectedMessages);

    }

    @Test
    @DisplayName("비밀번호가 짧은 경우")
    public void whenPasswordIsShort() {
        SignupReqDto signupReqDto = new SignupReqDto("validNickname", "short", "John Doe",
                "john.doe@example.com", "Hello!");

        Set<ConstraintViolation<SignupReqDto>> violations = validator.validate(signupReqDto);
        assertThat(violations).hasSize(1);

        ConstraintViolation<SignupReqDto> violation = violations.iterator().next();
        assertThat(violation.getMessage()).isEqualTo("password는 최소 10자 이상이어야합니다.");
    }

    @Test
    @DisplayName("이메일이 유효하지 않은 경우")
    public void whenEmailIsInvalid() {
        SignupReqDto signupReqDto = new SignupReqDto("validNickname", "validPassword", "John Doe",
                "invalid-email", "Hello!");

        Set<ConstraintViolation<SignupReqDto>> violations = validator.validate(signupReqDto);
        assertThat(violations).hasSize(1);

        ConstraintViolation<SignupReqDto> violation = violations.iterator().next();
        assertThat(violation.getMessage()).isEqualTo("올바른 이메일 형식이어야 합니다.");
    }

    @Test
    @DisplayName("한 줄 소개가 빈 경우")
    public void whenIntroduceIsBlank() {
        SignupReqDto signupReqDto = new SignupReqDto("validNickname", "validPassword123", "John Doe",
                "john.doe@example.com", "");

        Set<ConstraintViolation<SignupReqDto>> violations = validator.validate(signupReqDto);
        assertThat(violations).hasSize(1);

        ConstraintViolation<SignupReqDto> violation = violations.iterator().next();
        assertThat(violation.getMessage()).isEqualTo("한 줄 소개는 필수입니다.");
    }


    @Test
    @DisplayName("이름이 빈 경우")
    public void whenUsernameIsBlank() {
        SignupReqDto signupReqDto = new SignupReqDto("validNickname", "validPassword123", "",
                "john.doe@example.com", "Hello!");

        Set<ConstraintViolation<SignupReqDto>> violations = validator.validate(signupReqDto);
        assertThat(violations).hasSize(1);

        ConstraintViolation<SignupReqDto> violation = violations.iterator().next();
        assertThat(violation.getMessage()).isEqualTo("이름은 필수입니다.");
    }

}
