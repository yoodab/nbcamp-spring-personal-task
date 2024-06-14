package com.sparta.newspeed.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "에러 응답 DTO")
public class ErrorDto {
    @Schema(description = "에러 메시지")
    private String errormsg;

    @Schema(description = "에러 코드")
    private String errorcode;

    public ErrorDto(String errormsg, String errorcode) {
        this.errormsg = errormsg;
        this.errorcode = errorcode;
    }

}