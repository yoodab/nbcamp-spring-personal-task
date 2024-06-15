package com.sparta.newspeed.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProfileReqDto {

    private String username;
    private String introduce;
    private String password;
    private String email;
    private String changePassword; // 바꾼 비밀번호

    public void setChangePassword(String changePassword) {
        this.changePassword = changePassword;
    }

}
