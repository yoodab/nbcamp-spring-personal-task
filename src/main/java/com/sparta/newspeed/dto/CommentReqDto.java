package com.sparta.newspeed.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentReqDto {
    @Schema(description = "댓글 내용", example = "이것은 예시 댓글입니다.")
    private String content;
}
