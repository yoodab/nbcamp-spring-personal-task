package com.sparta.newspeed.dto;

import com.sparta.newspeed.entity.User;
import lombok.Getter;

@Getter
public class UserResDto {
    private String nickname; // 사용자 ID
    private String userName; // 사용자 이름
    private String email; // 사용자 이메일
    private String introduce; // 한줄소개



    public UserResDto(User user) {
        this.nickname = user.getNickname();
        this.userName = user.getUsername();
        this.email = user.getEmail();
        this.introduce = user.getIntroduce();
    }

}
