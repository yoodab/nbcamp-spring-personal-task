package com.sparta.newspeed.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProfileReqDto {

    @Schema(description = "이름", example = "John Doe")
    @NotBlank(message = "이름은 필수입니다.")
    private String username; // 사용자 이름
    @Schema(description = "한 줄 소개", example = "안녕하세요, 반갑습니다!")
    @NotBlank(message = "한 줄 소개는 필수입니다.")
    private String introduce; // 사용자 한 줄 소개

    private String password;
    @Schema(description = "이메일", example = "john.doe@example.com")
    @Email(message = "올바른 이메일 형식이어야 합니다.")
    private String email; // 사용자 이메일
    @Size(min = 10,  message = "password는 최소 10자 이상이어야합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "password는 알파벳 대소문자, 숫자로 구성되야합니다.")
    private String changePassword; // 바꾼 비밀번호

    public void setChangePassword(String changePassword) {
        this.changePassword = changePassword;
    }

}
