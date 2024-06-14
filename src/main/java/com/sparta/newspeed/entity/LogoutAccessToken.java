package com.sparta.newspeed.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Setter
@Table(name="logout_access_token")
public class LogoutAccessToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String logoutAccessToken;


    public LogoutAccessToken(String accessToken) {
        this.logoutAccessToken = accessToken;
    }
}
