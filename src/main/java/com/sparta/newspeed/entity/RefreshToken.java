package com.sparta.newspeed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "refreshtoken")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

    @Column
    private String refreshToken;

    @Column
    private boolean expired = false;


    public RefreshToken(User user) {

        this.expired = false;
        this.user = user;

    }

    public RefreshToken() {

    }


}
