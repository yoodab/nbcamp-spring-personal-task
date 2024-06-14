package com.sparta.newspeed.service;

import com.sparta.newspeed.dto.SignupReqDto;
import com.sparta.newspeed.dto.WithdrawReqDto;
import com.sparta.newspeed.entity.RefreshToken;
import com.sparta.newspeed.entity.User;
import com.sparta.newspeed.repository.UserRepository;
import com.sparta.newspeed.security.JwtUtil;
import com.sparta.newspeed.dto.UserServiceReqDto;
import com.sparta.newspeed.entity.UserStatusEnum;
import com.sparta.newspeed.security.UserDetailsImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;
    private final LogoutAccessTokenService LogoutAccessTokenService;
    private final JwtUtil jwtUtil;


    public String signup(SignupReqDto requestDto) {

        String nickName = requestDto.getNickname();
        String password = passwordEncoder.encode(requestDto.getPassword());
        requestDto.setPassword(password);

        // 회원 중복 확인
        Optional<User> checkNickName = userRepository.findByNickname(nickName);
        if (checkNickName.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }


        // 사용자 등록
        User user = new User(requestDto);

        userRepository.save(user);

        return "회원가입 성공";
    }

    @Transactional
    public String withdraw(User user, WithdrawReqDto withdrawReqDto) {
        if(passwordEncoder.matches(withdrawReqDto.getPassword(),user.getPassword())){
            user.withdraw();
            return "회원 탈퇴 성공";
        }else{
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }
    } // 회원 탈퇴


    public void login(UserServiceReqDto userServiceDto, HttpServletResponse res) {
        User user = findUserByNickname(userServiceDto.getNickname());

        if (checkUserId(user, userServiceDto)) {
            String accessToken = jwtUtil.createAccessToken(user.getNickname(), user.getUserStatus());
            String refreshToken = refreshTokenService.createRefreshToken(user);

            jwtUtil.addAccessJwtToHeader(accessToken, res);
            jwtUtil.addRefreshJwtToHeader(refreshToken, res);
        }
    }

    @Transactional
    public void logout(HttpServletRequest req, UserDetailsImpl userDetailsImpl) {
        String accessToken = jwtUtil.getAccessTokenFromHeader(req);
        accessToken = jwtUtil.substringAccessToken(accessToken);
        LogoutAccessTokenService.saveLogoutAccessToken(accessToken);

        User user = findUserByNickname(userDetailsImpl.getUser().getNickname());

        String refreshTokenValue = user.getRefreshToken();
        RefreshToken issuedRefreshToken = refreshTokenService.findByRefreshToken(refreshTokenValue);
        issuedRefreshToken.setExpired(true);
    } // 로그아웃


    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("등록된 회원이 아닙니다."));
    } // 유저 ID로 유저 찾기

    public User findUserByNickname(String id) {
        return userRepository.findByNickname(id).orElseThrow(() -> new IllegalArgumentException("등록된 회원이 아닙니다."));
    } // 유저 ID로 유저 찾기

    public Boolean checkUserId(User user, UserServiceReqDto userServiceReqDto) {
        if (!passwordEncoder.matches(userServiceReqDto.getPassword(),user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        } else if (!user.getUserStatus().equals(UserStatusEnum.NORMAL)) {
            throw new IllegalArgumentException("탈퇴한 회원입니다.");
        }
        return true;
    }
} // 로그인