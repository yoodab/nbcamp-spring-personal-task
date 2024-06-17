package com.sparta.newspeed.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "회원 탈퇴 요청 DTO")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawReqDto {
    @Schema(description = "비밀번호", example = "password123")
    private String password; // 비밀번호
}
