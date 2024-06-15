package com.sparta.newspeed.controller;

import com.sparta.newspeed.dto.ProfileReqDto;
import com.sparta.newspeed.dto.UserResDto;
import com.sparta.newspeed.security.UserDetailsImpl;
import com.sparta.newspeed.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")

public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/profile")
    public UserResDto getProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return profileService.getProfile(userDetails);
    }

    @PutMapping("/profile/{id}")
    public UserResDto updateProfile (@PathVariable Long id, @RequestBody ProfileReqDto profileReqDto) {
        return profileService.updateProfile(id, profileReqDto);
    }

}

