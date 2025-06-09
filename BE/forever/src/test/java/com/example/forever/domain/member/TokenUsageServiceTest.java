package com.example.forever.domain.member;

import com.example.forever.domain.Member;
import com.example.forever.exception.auth.MemberNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("TokenUsageService 단위 테스트")
class TokenUsageServiceTest {

    @Mock
    private MemberDomainRepository memberDomainRepository;

    @InjectMocks
    private TokenUsageService tokenUsageService;

    @Test
    @DisplayName("남은 토큰 사용량 조회 성공")
    void getRemainingUsage_Success() {
        // given
        Long memberId = 1L;
        MemberId memberIdVO = MemberId.of(memberId);
        Member member = createMemberWithTokens(5);

        when(memberDomainRepository.findById(memberId)).thenReturn(Optional.of(member));

        // when
        int remainingUsage = tokenUsageService.getRemainingUsage(memberIdVO);

        // then
        assertThat(remainingUsage).isEqualTo(5);
        verify(memberDomainRepository).findById(memberId);
    }

    @Test
    @DisplayName("존재하지 않는 회원의 토큰 사용량 조회 시 예외 발생")
    void getRemainingUsage_MemberNotFound_ThrowsException() {
        // given
        Long memberId = 999L;
        MemberId memberIdVO = MemberId.of(memberId);

        when(memberDomainRepository.findById(memberId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> tokenUsageService.getRemainingUsage(memberIdVO))
                .isInstanceOf(MemberNotFoundException.class);

        verify(memberDomainRepository).findById(memberId);
        verify(memberDomainRepository, never()).save(any());
    }


    @Test
    @DisplayName("토큰 갱신이 필요하지 않은 경우 저장하지 않음")
    void getRemainingUsage_NoRefreshNeeded_NoSave() {
        // given
        Long memberId = 1L;
        MemberId memberIdVO = MemberId.of(memberId);
        Member member = createMemberWithCurrentTokens();

        when(memberDomainRepository.findById(memberId)).thenReturn(Optional.of(member));

        // when
        int remainingUsage = tokenUsageService.getRemainingUsage(memberIdVO);

        // then
        assertThat(remainingUsage).isEqualTo(2);
        verify(memberDomainRepository).findById(memberId);
        verify(memberDomainRepository, never()).save(any());
    }

    @Test
    @DisplayName("토큰이 0개인 경우에도 정상 조회")
    void getRemainingUsage_ZeroTokens() {
        // given
        Long memberId = 1L;
        MemberId memberIdVO = MemberId.of(memberId);
        Member member = createMemberWithTokens(0);

        when(memberDomainRepository.findById(memberId)).thenReturn(Optional.of(member));

        // when
        int remainingUsage = tokenUsageService.getRemainingUsage(memberIdVO);

        // then
        assertThat(remainingUsage).isEqualTo(0);
    }

    private Member createMemberWithTokens(int availableTokens) {
        return Member.builder()
                .id(1L)
                .email("test@example.com")
                .nickname("테스트회원")
                .availableTokens(availableTokens)
                .lastTokenRefreshDate(LocalDate.now())
                .build();
    }

    private Member createMemberWithExpiredTokens() {
        return Member.builder()
                .id(1L)
                .email("test@example.com")
                .nickname("테스트회원")
                .availableTokens(0)
                .lastTokenRefreshDate(LocalDate.now().minusDays(1)) // 어제 날짜
                .build();
    }

    private Member createMemberWithCurrentTokens() {
        return Member.builder()
                .id(1L)
                .email("test@example.com")
                .nickname("테스트회원")
                .availableTokens(2)
                .lastTokenRefreshDate(LocalDate.now()) // 오늘 날짜
                .build();
    }
}
