package com.sparta.newspeed.controller;

import com.sparta.newspeed.dto.SignupReqDto;
import com.sparta.newspeed.dto.UserServiceReqDto;
import com.sparta.newspeed.dto.WithdrawReqDto;
import com.sparta.newspeed.repository.UserRepository;
import com.sparta.newspeed.security.UserDetailsImpl;
import com.sparta.newspeed.service.EmailService;
import com.sparta.newspeed.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@Tag(name = "회원 API", description = "회원가입/로그인/로그아웃/탈퇴 API")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final EmailService emailService;
    private final UserRepository userRepository;

    @Operation(summary = "회원가입", description = "회원가입을 수행합니다.")
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupReqDto signupRequestDto) throws MessagingException, UnsupportedEncodingException {
        String signupMessage = userService.signup(signupRequestDto);
        emailService.sendEmail(signupRequestDto.getEmail());
        return new ResponseEntity<>(signupMessage, HttpStatus.OK);
    } //회원 가입

    @PostMapping("/verify")
    public ResponseEntity<String> verifyCode(@RequestParam String email, @RequestParam String code) {
        boolean isVerified = emailService.verifyCode(email, code);
        if (isVerified) {

            return ResponseEntity.ok("인증 성공");
        } else {
            return ResponseEntity.status(400).body("인증 코드가 일치하지 않습니다.");
        }
    }//이메일인증

    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴를 수행합니다.")
    @PutMapping("/withdraw")
    public ResponseEntity<String> withdraw(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody WithdrawReqDto withdrawReqDto) {
        String withdrawMessage = userService.withdraw(userDetails.getUser(), withdrawReqDto);
        return new ResponseEntity<>(withdrawMessage, HttpStatus.OK);
    } // 회원 탈퇴


    @Operation(summary = "로그인", description = "로그인을 수행합니다.")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid UserServiceReqDto userServiceDto,
                                        HttpServletResponse res) {
        userService.login(userServiceDto, res);
        return new ResponseEntity<>("로그인 완료", HttpStatus.OK);
    } // 로그인

    @Operation(summary = "로그아웃", description = "로그아웃을 수행합니다.")
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest req,
                                         @Parameter(hidden = true) UserDetailsImpl userDetailsImpl) {
        userService.logout(req, userDetailsImpl);
        return new ResponseEntity<>("로그아웃 완료", HttpStatus.OK);
    } // 로그아웃


}
