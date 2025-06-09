package com.example.forever.controller;

import com.example.forever.application.token.TokenUsageApplicationService;
import com.example.forever.common.test.TestMemberArgumentResolver;
import com.example.forever.dto.member.TokenUsageResponse;
import com.example.forever.interfaces.web.auth.AuthController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("토큰 사용량 API 컨트롤러 테스트")
class TokenUsageControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TokenUsageApplicationService tokenUsageApplicationService;

    // 실제 AuthController의 모든 의존성을 Mock으로 생성
    @Mock
    private com.example.forever.application.member.MemberApplicationService memberApplicationService;
    @Mock
    private com.example.forever.application.auth.AuthenticationApplicationService authenticationApplicationService;
    @Mock
    private com.example.forever.application.member.MemberWithdrawalApplicationService memberWithdrawalApplicationService;
    @Mock
    private com.example.forever.application.auth.TokenRefreshApplicationService tokenRefreshApplicationService;

    @BeforeEach
    void setUp() {
        AuthController authController = new AuthController(
                memberApplicationService,
                authenticationApplicationService,
                memberWithdrawalApplicationService,
                tokenRefreshApplicationService,
                tokenUsageApplicationService
        );

        mockMvc = MockMvcBuilders.standaloneSetup(authController)
                .setCustomArgumentResolvers(new TestMemberArgumentResolver())
                .build();
    }

    @Test
    @DisplayName("토큰 사용량 조회 API 테스트 - 성공 (3개)")
    void getTokenUsage_Success_ThreeTokens() throws Exception {
        // given
        Long memberId = 1L;
        TokenUsageResponse response = new TokenUsageResponse(3);
        
        when(tokenUsageApplicationService.getRemainingUsage(memberId))
                .thenReturn(response);

        // when & then
        mockMvc.perform(get("/api/oauth/usage")
                        .header("Authorization", "Bearer test-token"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").isEmpty())
                .andExpect(jsonPath("$.data.remain_usage").value(3));

        verify(tokenUsageApplicationService).getRemainingUsage(memberId);
    }

    @Test
    @DisplayName("토큰 사용량 조회 API 테스트 - 성공 (0개)")
    void getTokenUsage_Success_ZeroTokens() throws Exception {
        // given
        Long memberId = 1L;
        TokenUsageResponse response = new TokenUsageResponse(0);
        
        when(tokenUsageApplicationService.getRemainingUsage(memberId))
                .thenReturn(response);

        // when & then
        mockMvc.perform(get("/api/oauth/usage")
                        .header("Authorization", "Bearer test-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.data.remain_usage").value(0));

        verify(tokenUsageApplicationService).getRemainingUsage(memberId);
    }

    @Test
    @DisplayName("토큰 사용량 조회 API 테스트 - 성공 (최대값)")
    void getTokenUsage_Success_MaxTokens() throws Exception {
        // given
        Long memberId = 1L;
        TokenUsageResponse response = new TokenUsageResponse(10);
        
        when(tokenUsageApplicationService.getRemainingUsage(memberId))
                .thenReturn(response);

        // when & then
        mockMvc.perform(get("/api/oauth/usage")
                        .header("Authorization", "Bearer test-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.remain_usage").value(10));
    }


    @Test
    @DisplayName("토큰 사용량 조회 API 테스트 - GET 메서드만 허용")
    void getTokenUsage_OnlyGetAllowed() throws Exception {
        // when & then - POST 메서드는 허용되지 않음
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .post("/api/oauth/usage")
                        .header("Authorization", "Bearer test-token"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    @DisplayName("토큰 사용량 조회 API 응답 형식 검증")
    void getTokenUsage_ResponseFormat() throws Exception {
        // given
        Long memberId = 1L;
        TokenUsageResponse response = new TokenUsageResponse(2);
        
        when(tokenUsageApplicationService.getRemainingUsage(memberId))
                .thenReturn(response);

        // when & then
        mockMvc.perform(get("/api/oauth/usage")
                        .header("Authorization", "Bearer test-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.remain_usage").exists())
                .andExpect(jsonPath("$.data.remain_usage").isNumber());
    }
}
