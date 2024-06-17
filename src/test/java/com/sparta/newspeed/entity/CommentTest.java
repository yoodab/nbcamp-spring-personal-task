package com.sparta.newspeed.entity;

import com.sparta.newspeed.dto.CommentReqDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Comment Entity 테스트")
public class CommentTest {

    @Test
    @DisplayName("댓글 생성 및 초기화")
    void testCommentCreation() {
        User user = new User();
        Peed peed = new Peed();
        CommentReqDto commentReqDto = new CommentReqDto("테스트 댓글 내용");

        Comment comment = new Comment(commentReqDto, user, peed);

        assertThat(commentReqDto.getContent()).isEqualTo(comment.getContent());
        assertThat(user).isEqualTo(comment.getUser());
        assertThat(peed).isEqualTo(comment.getPeed());
        assertThat(comment.getLikesCount()).isEqualTo(0);

    }

    @Test
    @DisplayName("좋아요 추가")
    void testAddLike() {
        CommentReqDto commentReqDto = new CommentReqDto("테스트 댓글 내용");
        User user = new User();
        Peed peed = new Peed();
        Comment comment = new Comment(commentReqDto, user, peed);

        comment.likesCount("+");
        assertThat(comment.getLikesCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("좋아요 제거")
    void testRemoveLike() {
        CommentReqDto commentReqDto = new CommentReqDto("테스트 댓글 내용");
        User user = new User();
        Peed peed = new Peed();
        Comment comment = new Comment(commentReqDto, user, peed);
        comment.likesCount("+");

        comment.likesCount("-");


        assertThat(comment.getLikesCount()).isEqualTo(0);
    }
}