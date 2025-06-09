package com.example.forever.application.member;

import com.example.forever.application.token.TokenUsageApplicationService;
import com.example.forever.dto.member.TokenUsageResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("TokenUsageApplicationService 단위 테스트")
class TokenUsageApplicationServiceTest {

    @Mock
    private TokenUsageApplicationService tokenUsageApplicationService;

    @Test
    @DisplayName("남은 토큰 사용량 조회 성공 - 3개")
    void getRemainingUsage_Success_ThreeTokens() {
        // given
        Long memberId = 1L;
        when(tokenUsageApplicationService.getRemainingUsage(memberId)).thenReturn(new TokenUsageResponse(3));

        // when
        TokenUsageResponse response = tokenUsageApplicationService.getRemainingUsage(memberId);

        // then
        assertThat(response.remainUsage()).isEqualTo(3);
        verify(tokenUsageApplicationService).getRemainingUsage(memberId);
    }

    @Test
    @DisplayName("남은 토큰 사용량 조회 성공 - 0개")
    void getRemainingUsage_Success_ZeroTokens() {
        // given
        Long memberId = 1L;
        when(tokenUsageApplicationService.getRemainingUsage(memberId)).thenReturn(new TokenUsageResponse(0));

        // when
        TokenUsageResponse response = tokenUsageApplicationService.getRemainingUsage(memberId);

        // then
        assertThat(response.remainUsage()).isEqualTo(0);
        verify(tokenUsageApplicationService).getRemainingUsage(memberId);
    }

    @Test
    @DisplayName("남은 토큰 사용량 조회 성공 - 최대값")
    void getRemainingUsage_Success_MaxTokens() {
        // given
        Long memberId = 1L;
        when(tokenUsageApplicationService.getRemainingUsage(memberId)).thenReturn(new TokenUsageResponse(10));

        // when
        TokenUsageResponse response = tokenUsageApplicationService.getRemainingUsage(memberId);

        // then
        assertThat(response.remainUsage()).isEqualTo(10);
        verify(tokenUsageApplicationService).getRemainingUsage(memberId);
    }
}
