package com.sparta.newspeed.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "로그인 요청 DTO")
public class UserServiceReqDto {

    @Schema(description = "사용자 닉네임", example = "user123")
    @NotBlank(message = "nickname은 필수입니다.")
    @Size(min = 10, max = 20, message = "nickname은 최소 10자 이상, 20자 이하여야합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "nickname은 알파벳 대소문자, 숫자로 구성되야합니다.")
    private String nickname;

    @Schema(description = "비밀번호", example = "password123")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "password는 알파벳 대소문자, 숫자로 구성되야합니다.")
    private String password;
}
