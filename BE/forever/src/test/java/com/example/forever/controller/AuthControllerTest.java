package com.example.forever.controller;


import com.example.forever.common.test.TestMemberArgumentResolver;
import com.example.forever.dto.member.SignUpRequest;
import com.example.forever.application.auth.LoginResult;
import com.example.forever.application.member.WithdrawResult;
import com.example.forever.interfaces.web.auth.AuthController;
import com.example.forever.application.member.MemberWithdrawalApplicationService;
import com.example.forever.application.member.MemberApplicationService;
import com.example.forever.application.auth.TokenRefreshApplicationService;
import com.example.forever.application.auth.AuthenticationApplicationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    
    @Mock
    private MemberApplicationService memberApplicationService;
    
    @Mock
    private AuthenticationApplicationService authenticationApplicationService;
    
    @Mock
    private MemberWithdrawalApplicationService memberWithdrawalApplicationService;
    
    @Mock
    private TokenRefreshApplicationService tokenRefreshApplicationService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(authController)
                .setCustomArgumentResolvers(new TestMemberArgumentResolver())
                .setHandlerExceptionResolvers(new ExceptionHandlerExceptionResolver())
                .build();
    }

    @Test
    @DisplayName("카카오 로그인 API 테스트 - 성공")
    void oAuthLogin_Success() throws Exception {
        // Given
        String code = "valid-kakao-code";
        LoginResult loginResult = new LoginResult("테스트", "컴퓨터공학", "테스트대학교", null);
        
        when(authenticationApplicationService.login(any(), any())).thenReturn(loginResult);

        // When & Then
        mockMvc.perform(get("/api/oauth/kakao")
                        .param("code", code))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value(loginResult.name()))
                .andExpect(jsonPath("$.data.major").value(loginResult.major()))
                .andExpect(jsonPath("$.data.school").value(loginResult.school()));

        verify(authenticationApplicationService).login(any(), any());
    }

    @Test
    @DisplayName("카카오 회원가입 API 테스트 - 성공")
    void oAuthSignup_Success() throws Exception {
        // Given
        SignUpRequest request = new com.example.forever.dto.member.SignUpRequest(
                "테스트",
                "컴퓨터공학",
                "테스트대학교",
                "test@example.com",
                List.of("인플로우1", "인플로우2"),
                true
        );
        
        doNothing().when(memberApplicationService).signUp(any(), any());

        // When & Then
        mockMvc.perform(post("/api/oauth/kakao/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(memberApplicationService).signUp(any(), any());
    }

    @Test
    @DisplayName("카카오 회원 탈퇴 API 테스트 - 성공")
    void oAuthQuit_Success() throws Exception {
        // Given
        WithdrawResult withdrawResult = WithdrawResult.createSuccess();
        when(memberWithdrawalApplicationService.withdraw(any())).thenReturn(withdrawResult);

        // When & Then
        mockMvc.perform(post("/api/oauth/quit"))
                .andExpect(status().isOk());

        verify(memberWithdrawalApplicationService).withdraw(any());
    }

    @Test
    @DisplayName("토큰 갱신 API 테스트 - 성공")
    void refreshToken_Success() throws Exception {
        // Given
        String refreshToken = "valid-refresh-token";
        
        doNothing().when(tokenRefreshApplicationService).refreshToken(eq(refreshToken), any());

        // When & Then
        mockMvc.perform(post("/api/oauth/refresh")
                        .cookie(new Cookie("refresh_token", refreshToken)))
                .andExpect(status().isOk());

        verify(tokenRefreshApplicationService).refreshToken(eq(refreshToken), any());
    }
}
