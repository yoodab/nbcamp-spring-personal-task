package com.sparta.newspeed.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class PeedRequestDto {
    @Schema(description = "피드 내용", example = "예시내용입니다.")
    @NotBlank(message = "피드 내용은 필수입니다.")
    private String contents;
}
