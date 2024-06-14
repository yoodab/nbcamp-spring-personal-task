package com.sparta.newspeed.repository;

import com.sparta.newspeed.entity.LogoutAccessToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LogoutAccessTokenRepository extends JpaRepository<LogoutAccessToken, Long> {

    Optional<LogoutAccessToken> findByLogoutAccessToken(String token);
}
