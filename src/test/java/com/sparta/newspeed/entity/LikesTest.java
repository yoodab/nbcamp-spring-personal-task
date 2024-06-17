package com.sparta.newspeed.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Likes Entity 테스트")
public class LikesTest {

    @Test
    @DisplayName("Comment를 이용한 Likes 생성")
    public void testLikesCreationWithComment() {
        Comment comment = new Comment();
        User user = new User();
        ContentTypeEnum contentTypeEnum = ContentTypeEnum.COMMENT;

        Likes likes = new Likes(comment, user, contentTypeEnum);

        assertThat(likes).isNotNull();
        assertThat(likes.getContentType()).isEqualTo(contentTypeEnum);
        assertThat(likes.getContentId()).isEqualTo(comment.getId());
        assertThat(likes.getUser()).isEqualTo(user);
    }

    @Test
    @DisplayName("Peed를 이용한 Likes 생성")
    public void testLikesCreationWithPeed() {
        Peed peed = new Peed();
        User user = new User();
        ContentTypeEnum contentTypeEnum = ContentTypeEnum.PEED;

        Likes likes = new Likes(peed, user, contentTypeEnum);

        assertThat(likes).isNotNull();
        assertThat(likes.getContentType()).isEqualTo(contentTypeEnum);
        assertThat(likes.getContentId()).isEqualTo(peed.getId());
        assertThat(likes.getUser()).isEqualTo(user);
    }
}