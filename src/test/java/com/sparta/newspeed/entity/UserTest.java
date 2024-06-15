package com.sparta.newspeed.entity;

import com.sparta.newspeed.dto.ProfileReqDto;
import com.sparta.newspeed.dto.SignupReqDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("User Entity 테스트")
public class UserTest {
    private SignupReqDto signupReqDto;
    private User user;

    @BeforeEach
    void setUp() {
        signupReqDto = new SignupReqDto("nickname", "password", "username", "email@example.com", "introduce");
        user = new User(signupReqDto);
    }

    @Test
    @DisplayName("User 생성")
    void testUserCreation() {
        assertEquals("nickname", user.getNickname());
        assertEquals("password", user.getPassword());
        assertEquals("username", user.getUsername());
        assertEquals("email@example.com", user.getEmail());
        assertEquals("introduce", user.getIntroduce());
        assertEquals(UserStatusEnum.NORMAL, user.getUserStatus());
    }

    @Test
    @DisplayName("User 탈퇴")
    void testWithdraw() {
        user.withdraw();
        assertEquals(UserStatusEnum.WITHDREW, user.getUserStatus());
    }

    @Test
    @DisplayName("User 업데이트")
    void testUpdateProfile() {
        ProfileReqDto profileReqDto = new ProfileReqDto("newUsername", "newIntroduce", "password", "newEmail@example.com", "newPassword");
        user.update(profileReqDto);

        assertEquals("newUsername", user.getUsername());
        assertEquals("newEmail@example.com", user.getEmail());
        assertEquals("newIntroduce", user.getIntroduce());
        assertEquals("newPassword", user.getPassword());
    }
}
