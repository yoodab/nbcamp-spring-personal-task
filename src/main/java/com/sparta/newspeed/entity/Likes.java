package com.sparta.newspeed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "likes")
public class Likes extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ContentTypeEnum contentType;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Column(name="content_id", nullable = false)
    private Long contentId;

    public Likes(Comment comment, User user, ContentTypeEnum contentTypeEnum) {
        this.contentId = comment.getId();
        this.user = user;
        this.contentType = contentTypeEnum;
        this.user.getLikesList().add(this);
//        comment.getLikesList().add(this);

    }

    public Likes(Peed peed, User user, ContentTypeEnum contentTypeEnum) {
        this.contentId = peed.getId();
        this.user = user;
        this.contentType = contentTypeEnum;
        this.user.getLikesList().add(this);
//        peed.getLikesList().add(this);
    }
}
