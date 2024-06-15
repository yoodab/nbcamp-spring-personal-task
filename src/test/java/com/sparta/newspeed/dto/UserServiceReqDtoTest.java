package com.sparta.newspeed.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@DisplayName("UserServiceReqDto 테스트")
public class UserServiceReqDtoTest {

    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }


    @Test
    @DisplayName("모든 필드가 유효할 때")
    public void whenAllFieldsAreValid() {
        UserServiceReqDto dto = new UserServiceReqDto("validNickname", "validPassword123");
        Set<ConstraintViolation<UserServiceReqDto>> violations = validator.validate(dto);
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("닉네임이 빈 경우")
    public void whenNicknameIsBlank() {
        UserServiceReqDto dto = new UserServiceReqDto("", "validPassword123");

        Set<ConstraintViolation<UserServiceReqDto>> violations = validator.validate(dto);
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
    @DisplayName("닉네임이 길이 제한을 초과하는 경우")
    public void whenNicknameLengthExceedsLimit() {
        UserServiceReqDto dto = new UserServiceReqDto("toolongnickname123456789", "validPassword123");

        Set<ConstraintViolation<UserServiceReqDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);

        ConstraintViolation<UserServiceReqDto> violation = violations.iterator().next();
        assertThat(violation.getMessage()).isEqualTo("nickname은 최소 10자 이상, 20자 이하여야합니다.");
    }

    @Test
    @DisplayName("닉네임이 특정 패턴에 맞지 않는 경우")
    public void whenNicknamePatternIsInvalid() {
        UserServiceReqDto dto = new UserServiceReqDto("invalid_nickname", "validPassword123");

        Set<ConstraintViolation<UserServiceReqDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);

        ConstraintViolation<UserServiceReqDto> violation = violations.iterator().next();
        assertThat(violation.getMessage()).isEqualTo("nickname은 알파벳 대소문자, 숫자로 구성되야합니다.");
    }

    @Test
    @DisplayName("비밀번호 패턴이 유효하지 않은 경우")
    public void whenPasswordPatternIsInvalid() {
        UserServiceReqDto dto = new UserServiceReqDto("validNickname", "invalid password");

        Set<ConstraintViolation<UserServiceReqDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);

        ConstraintViolation<UserServiceReqDto> violation = violations.iterator().next();
        assertThat(violation.getMessage()).isEqualTo("password는 알파벳 대소문자, 숫자로 구성되야합니다.");
    }
}
