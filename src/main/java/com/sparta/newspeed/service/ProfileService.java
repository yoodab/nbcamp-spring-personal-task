package com.sparta.newspeed.service;

import com.sparta.newspeed.dto.ProfileReqDto;
import com.sparta.newspeed.dto.UserResDto;
import com.sparta.newspeed.entity.User;
import com.sparta.newspeed.repository.UserRepository;
import com.sparta.newspeed.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 프로필 조회
     *
     * @param userDetails
     * @return userResDto
     */
    public UserResDto getProfile(UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        UserResDto userResDto = new UserResDto(user);
        return userResDto;
    }

    /**
     * 프로필 수정
     *
     * @param id, profileReqDto
     * @return UserResDto
     */
    @Transactional
    public UserResDto updateProfile(Long id, ProfileReqDto profileReqDto) {
        User user2 = getUserById(id);
        if (!profileReqDto.getChangePassword().isEmpty()) {

            // 로그인한 비밀번호와 다를 때
            if (!passwordEncoder.matches(profileReqDto.getPassword(), user2.getPassword())) {
                throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
            }
            // 변경한 비밀번호와 기존 비밀번호가 동일 할 때
            if (profileReqDto.getChangePassword().equals(profileReqDto.getPassword())) {
                throw new IllegalArgumentException("비밀번호가 동일합니다.");
            }

            profileReqDto.setChangePassword(passwordEncoder.encode(profileReqDto.getChangePassword()));
            user2.update(profileReqDto);
        }
        return new UserResDto(user2);
    }

    private User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("프로필이 없습니다"));
    }
}






