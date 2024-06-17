package com.sparta.newspeed.entity;

import com.sparta.newspeed.dto.CommentReqDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "peed_id", nullable = false)
    private Peed peed;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

   private int likesCount;

    public Comment(CommentReqDto commentReqDto, User user, Peed peed) {
        this.content = commentReqDto.getContent();
        this.user = user;
        this.peed = peed;
        this.user.getCommentList().add(this);
        this.peed.getCommentList().add(this);
    }

    public void likesCount (String oper) {
        if(oper.equals("+")) {
            this.likesCount++;
        } else if(oper.equals("-")) {
            this.likesCount--;
        }

    }

}
