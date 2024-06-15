package com.sparta.newspeed.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PeedRequestDto {

    @Schema(description = "작성자 이름", example = "JohnDoe")
    private String username;

    @Schema(description = "피드 내용", example = "예시내용입니다.")
    private String contents;
}
