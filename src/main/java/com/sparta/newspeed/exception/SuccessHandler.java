package com.sparta.newspeed.exception;

public class SuccessHandler extends RuntimeException{
    private final int statusCode;

    public SuccessHandler(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
