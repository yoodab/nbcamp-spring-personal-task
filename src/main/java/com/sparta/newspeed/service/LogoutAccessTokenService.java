package com.sparta.newspeed.service;

import com.sparta.newspeed.entity.LogoutAccessToken;
import com.sparta.newspeed.entity.RefreshToken;
import com.sparta.newspeed.repository.LogoutAccessTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LogoutAccessTokenService {
    private final LogoutAccessTokenRepository logoutAccessTokenRepository;


    public void saveLogoutAccessToken(String accessToken) {
        logoutAccessTokenRepository.save(new LogoutAccessToken(accessToken));
    }

    public boolean isExistLogoutToken(String accessToken) {
        Optional<LogoutAccessToken> logoutAccessToken = logoutAccessTokenRepository.findByLogoutAccessToken(accessToken);
        if (logoutAccessToken.isPresent()) {
            return true;
        }
        return false;
    }



}
