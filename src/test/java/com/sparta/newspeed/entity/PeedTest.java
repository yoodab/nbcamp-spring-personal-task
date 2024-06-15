package com.sparta.newspeed.entity;

import com.sparta.newspeed.dto.PeedRequestDto;
import com.sparta.newspeed.dto.SignupReqDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Peed 엔티티 테스트")
public class PeedTest {

    private User user;
    private PeedRequestDto requestDto;

    @BeforeEach
    public void setUp() {
        // 테스트용 User 생성
        SignupReqDto signupReqDto = new SignupReqDto("validNickname", "validPassword", "John Doe",
                "john.doe@example.com", "Hello!");

        user = new User(signupReqDto);

        // 테스트용 PeedRequestDto 생성
        requestDto = new PeedRequestDto("이것은 테스트 콘텐츠입니다.");
    }

    @Test
    @DisplayName("Peed 생성자 테스트")
    public void testPeedConstructor() {
        Peed peed = new Peed(requestDto, user);

        assertThat(peed.getContents()).isEqualTo(requestDto.getContents());
        assertThat(peed.getUser()).isEqualTo(user);
        assertThat(user.getPeedlist()).contains(peed);
    }

    @Test
    @DisplayName("Peed 업데이트 테스트")
    public void testPeedUpdate() {
        Peed peed = new Peed(requestDto, user);

        PeedRequestDto updateDto = new PeedRequestDto("업데이트된 콘텐츠입니다.");

        peed.update(updateDto);

        assertThat(peed.getContents()).isEqualTo("업데이트된 콘텐츠입니다.");
    }

    @Test
    @DisplayName("좋아요 카운트 증가 테스트")
    public void testLikesCountIncrement() {
        Peed peed = new Peed(requestDto, user);
        int initialLikesCount = peed.getLikesCount();

        peed.likesCount("+");

        assertThat(peed.getLikesCount()).isEqualTo(initialLikesCount + 1);
    }

    @Test
    @DisplayName("좋아요 카운트 감소 테스트")
    public void testLikesCountDecrement() {
        Peed peed = new Peed(requestDto, user);
        peed.likesCount("+"); // 먼저 증가시킨 후 감소시킴
        int initialLikesCount = peed.getLikesCount();

        peed.likesCount("-");

        assertThat(peed.getLikesCount()).isEqualTo(initialLikesCount - 1);
    }
}
