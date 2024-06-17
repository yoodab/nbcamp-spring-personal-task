package com.sparta.newspeed.entity;

import com.sparta.newspeed.dto.ProfileReqDto;
import com.sparta.newspeed.dto.SignupReqDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat("nickname").isEqualTo(user.getNickname());
        assertThat("password").isEqualTo(user.getPassword());
        assertThat("username").isEqualTo(user.getUsername());
        assertThat("email@example.com").isEqualTo(user.getEmail());
        assertThat("introduce").isEqualTo(user.getIntroduce());
        assertThat(UserStatusEnum.NORMAL).isEqualTo(user.getUserStatus());
    }

    @Test
    @DisplayName("User 탈퇴")
    void testWithdraw() {

        user.withdraw();

        assertThat(UserStatusEnum.WITHDREW).isEqualTo(user.getUserStatus());
    }

    @Test
    @DisplayName("User 업데이트")
    void testUpdateProfile() {
        ProfileReqDto profileReqDto = new ProfileReqDto("newUsername", "newIntroduce", "password", "newEmail@example.com", "newPassword");

        user.update(profileReqDto);

        assertThat("newPassword").isEqualTo(user.getPassword());
        assertThat("newUsername").isEqualTo(user.getUsername());
        assertThat("newEmail@example.com").isEqualTo(user.getEmail());
        assertThat("newIntroduce").isEqualTo(user.getIntroduce());
    }
}
