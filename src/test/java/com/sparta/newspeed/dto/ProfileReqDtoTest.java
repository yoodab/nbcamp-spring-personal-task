package com.sparta.newspeed.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ProfileReqDto 테스트")
public class ProfileReqDtoTest {

    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("모든 필드가 유효할 때")
    public void whenAllFieldsAreValid() {
        ProfileReqDto profileReqDto = new ProfileReqDto("John Doe", "Hello!", "validPassword123",
                "john.doe@example.com", "newValidPassword123");

        Set<ConstraintViolation<ProfileReqDto>> violations = validator.validate(profileReqDto);
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("이름이 빈 경우")
    public void whenUsernameIsBlank() {
        ProfileReqDto profileReqDto = new ProfileReqDto("", "Hello!", "validPassword123",
                "john.doe@example.com", "newValidPassword123");

        Set<ConstraintViolation<ProfileReqDto>> violations = validator.validate(profileReqDto);
        assertThat(violations).hasSize(1);

        ConstraintViolation<ProfileReqDto> violation = violations.iterator().next();
        assertThat(violation.getMessage()).isEqualTo("이름은 필수입니다.");
    }

    @Test
    @DisplayName("한 줄 소개가 빈 경우")
    public void whenIntroduceIsBlank() {
        ProfileReqDto profileReqDto = new ProfileReqDto("John Doe", "", "validPassword123",
                "john.doe@example.com", "newValidPassword123");

        Set<ConstraintViolation<ProfileReqDto>> violations = validator.validate(profileReqDto);
        assertThat(violations).hasSize(1);

        ConstraintViolation<ProfileReqDto> violation = violations.iterator().next();
        assertThat(violation.getMessage()).isEqualTo("한 줄 소개는 필수입니다.");
    }


    @Test
    @DisplayName("이메일이 유효하지 않은 경우")
    public void whenEmailIsInvalid() {
        ProfileReqDto profileReqDto = new ProfileReqDto("John Doe", "Hello!", "validPassword123",
                "invalid-email", "newValidPassword123");

        Set<ConstraintViolation<ProfileReqDto>> violations = validator.validate(profileReqDto);
        assertThat(violations).hasSize(1);

        ConstraintViolation<ProfileReqDto> violation = violations.iterator().next();
        assertThat(violation.getMessage()).isEqualTo("올바른 이메일 형식이어야 합니다.");
    }

    @Test
    @DisplayName("바꾼 비밀번호가 짧은 경우")
    public void whenChangePasswordIsShort() {
        ProfileReqDto profileReqDto = new ProfileReqDto("John Doe", "Hello!", "validPassword123",
                "john.doe@example.com", "short");

        Set<ConstraintViolation<ProfileReqDto>> violations = validator.validate(profileReqDto);
        assertThat(violations).hasSize(1);

        ConstraintViolation<ProfileReqDto> violation = violations.iterator().next();
        assertThat(violation.getMessage()).isEqualTo("password는 최소 10자 이상이어야합니다.");
    }

    @Test
    @DisplayName("바꾼 비밀번호에 특수문자 포함")
    public void whenChangePasswordContainsSpecialCharacters() {
        ProfileReqDto profileReqDto = new ProfileReqDto("John Doe", "Hello!", "validPassword123",
                "john.doe@example.com", "newValidPassword!@#");

        Set<ConstraintViolation<ProfileReqDto>> violations = validator.validate(profileReqDto);
        assertThat(violations).isEmpty();
    }
}