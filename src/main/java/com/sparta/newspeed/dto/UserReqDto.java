package com.sparta.newspeed.dto;

import lombok.Getter;

@Getter
public class UserReqDto {
    private String nickname; // 사용자 ID
    private String username; // 사용자 이름
    private String password; // 사용자 비밀번호
    private String email; // 사용자 이메일
    private String introduce; // 한줄소개

}