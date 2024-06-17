package com.sparta.newspeed.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.newspeed.MockSpringSecurityFilter;
import com.sparta.newspeed.dto.SignupReqDto;
import com.sparta.newspeed.dto.UserServiceReqDto;
import com.sparta.newspeed.dto.WithdrawReqDto;
import com.sparta.newspeed.entity.User;
import com.sparta.newspeed.repository.UserRepository;
import com.sparta.newspeed.security.UserDetailsImpl;
import com.sparta.newspeed.security.WebSecurityConfig;
import com.sparta.newspeed.service.EmailService;
import com.sparta.newspeed.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(
        controllers = {UserController.class,EmailService.class},
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = WebSecurityConfig.class
                )
        }
)
class UserControllerTest {
    private MockMvc mvc;

    private Principal mockPrincipal;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;


    @MockBean
    private UserService userService;

    @MockBean
    private EmailService emailService;

    @MockBean
    private UserRepository userRepository;



    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity(new MockSpringSecurityFilter()))
                .build();
        mockUserSetup();
    }

    private void mockUserSetup() {
        // Mock 테스트 유저 생성
        SignupReqDto signupReqDto = new SignupReqDto("validNickname", "validPassword", "John Doe",


                "john.doe@example.com", "Hello!");
        User testUser = new User(signupReqDto);  // SignupReqDto를 사용하여 User 객체 생성
        testUser.setId(1L);  // 예를 들어, 테스트용으로 ID 설정
        UserDetailsImpl testUserDetails = new UserDetailsImpl(testUser);

        // 테스트용 Principal 설정
        mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails, testUser.getPassword(), testUserDetails.getAuthorities());
    }

    @Test
    @Order(1)
    @DisplayName("회원가입 요청 처리")
    void testSignup() throws Exception {
        // given
        SignupReqDto signupReqDto = new SignupReqDto("validNickname", "validPassword", "John Doe",
                                                     "john.doe@example.com", "Hello!");
        given(userService.signup(any(SignupReqDto.class))).willReturn("회원가입 성공");
        given(emailService.sendEmail(anyString())).willReturn(String.valueOf(true));

        System.out.println("signupReqDto :: " + objectMapper.writeValueAsString(signupReqDto));
        // when - then
        ResultActions resultActions = mvc.perform(post("/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signupReqDto)));

        // then
        MvcResult mvcResult = resultActions
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        System.out.println("mvcResult :: " + mvcResult.getResponse().getContentAsString());
    }



    @Test
    @Order(2)
    @DisplayName("로그인 요청 처리")
    void testLogin() throws Exception {
        // given
        UserServiceReqDto userServiceReqDto = new UserServiceReqDto("validNickname", "validPassword");
        willDoNothing().given(userService).login(any(UserServiceReqDto.class), any(HttpServletResponse.class));

        // when - then
        mvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userServiceReqDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("로그인 완료"))
                .andDo(print());
    }

    @Test
    @DisplayName("로그아웃 요청 처리")
    void testLogout() throws Exception {

        // given
        willDoNothing().given(userService).logout(any(HttpServletRequest.class), any(UserDetailsImpl.class));

        // when - then
        mvc.perform(post("/user/logout")
                        .principal(mockPrincipal))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("로그아웃 완료"))
                .andDo(print());
    }

    @Test
    @Order(3)
    @DisplayName("회원 탈퇴 요청 처리")
    void testWithdraw() throws Exception {
        // given
        WithdrawReqDto withdrawReqDto = new WithdrawReqDto("validPassword");
        given(userService.withdraw(any(User.class), any(WithdrawReqDto.class))).willReturn("회원 탈퇴 성공");

        // when - then
        mvc.perform(put("/user/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(withdrawReqDto))
                        .principal(mockPrincipal))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("회원 탈퇴 성공"))
                .andDo(print());
    }
}