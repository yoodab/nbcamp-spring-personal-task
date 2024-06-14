package com.sparta.newspeed.service;

import com.sparta.newspeed.entity.RefreshToken;
import com.sparta.newspeed.entity.User;
import com.sparta.newspeed.repository.RefreshTokenRepository;
import com.sparta.newspeed.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public String createRefreshToken(User user) {

        String token = jwtUtil.createRefreshToken();
        String tokenValue = jwtUtil.substringRefreshToken(token);
        RefreshToken refreshToken = findRefreshTokenByUser(user);
        refreshToken.setRefreshToken(tokenValue);
        user.setRefreshToken(tokenValue);
        refreshTokenRepository.save(refreshToken);
        return token;

    }

    private RefreshToken findRefreshTokenByUser(User user) {
        return refreshTokenRepository.findByUser(user).orElse(new RefreshToken(user));

    }

    @Transactional
    public String updateRefreshToken(RefreshToken refreshToken) {

        RefreshToken refreshToken1 = findByRefreshToken(refreshToken.getRefreshToken());
        String token = jwtUtil.createRefreshToken();
        String tokenValue = jwtUtil.substringRefreshToken(token);
        refreshToken1.setRefreshToken(tokenValue);

        return token;

    }


    public RefreshToken findByRefreshToken(String tokenValue) {
        return refreshTokenRepository.findByRefreshToken(tokenValue)
                .orElseThrow(() -> new IllegalArgumentException
                        ("RefreshTokenService 53 : 리프레쉬 토큰을 찾을 수 없습니다."));

    }


    public void deleteRefreshToken(RefreshToken issuedRefreshToken) {
        refreshTokenRepository.delete(issuedRefreshToken);
    }
}