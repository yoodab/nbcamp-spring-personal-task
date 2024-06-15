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

@DisplayName("PeedRequestDto 테스트")
public class PeedRequestDtoTest {

    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }


    @Test
    @DisplayName("정상적인 내용으로 생성")
    public void whenContentIsValid() {
        PeedRequestDto peedRequestDto = new PeedRequestDto("예시내용입니다.");

        Set<ConstraintViolation<PeedRequestDto>> violations = validator.validate(peedRequestDto);
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("내용이 비어있을 때")
    public void whenContentIsBlank() {
        PeedRequestDto peedRequestDto = new PeedRequestDto("");

        Set<ConstraintViolation<PeedRequestDto>> violations = validator.validate(peedRequestDto);
        assertThat(violations).hasSize(1);

        ConstraintViolation<PeedRequestDto> violation = violations.iterator().next();
        assertThat(violation.getMessage()).isEqualTo("피드 내용은 필수입니다.");

    }
}