package com.sparta.newspeed.repository;

import com.sparta.newspeed.entity.RefreshToken;
import com.sparta.newspeed.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByRefreshToken(String RefreshTokenValue);


    Optional<RefreshToken> findByUser(User user);
}
