package com.sparta.newspeed.exception;

import com.sparta.newspeed.dto.ErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler({ SuccessHandler.class})

    public ResponseEntity<ErrorDto> handleCustomExceptions(RuntimeException ex) {
        int statusCode;
        if (ex instanceof SuccessHandler) {
            statusCode = ((SuccessHandler) ex).getStatusCode();
        }else{
            statusCode = HttpStatus.BAD_REQUEST.value(); // 기본 상태 코드
        }
        ErrorDto errorDto = new ErrorDto(ex.getMessage(), String.valueOf(statusCode));
        return new ResponseEntity<>(errorDto, HttpStatus.valueOf(statusCode));
    }
}
