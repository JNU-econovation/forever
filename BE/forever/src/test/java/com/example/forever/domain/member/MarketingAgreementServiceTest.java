package com.example.forever.domain.member;

import com.example.forever.domain.Member;
import com.example.forever.exception.auth.MemberNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("MarketingAgreementService 단위 테스트")
class MarketingAgreementServiceTest {

    @Mock
    private MemberDomainRepository memberDomainRepository;

    @InjectMocks
    private MarketingAgreementService marketingAgreementService;

    @Test
    @DisplayName("마케팅 동의 정보 조회 성공")
    void getMarketingAgreement_Success() {
        // given
        Long memberId = 1L;
        MemberId memberIdVO = MemberId.of(memberId);
        Member member = createMemberWithMarketingAgreement(true);

        when(memberDomainRepository.findById(memberId)).thenReturn(Optional.of(member));

        // when
        MarketingAgreement result = marketingAgreementService.getMarketingAgreement(memberIdVO);

        // then
        assertThat(result.isAgreed()).isTrue();
        verify(memberDomainRepository).findById(memberId);
    }

    @Test
    @DisplayName("존재하지 않는 회원의 마케팅 동의 정보 조회 시 예외 발생")
    void getMarketingAgreement_MemberNotFound_ThrowsException() {
        // given
        Long memberId = 999L;
        MemberId memberIdVO = MemberId.of(memberId);

        when(memberDomainRepository.findById(memberId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> marketingAgreementService.getMarketingAgreement(memberIdVO))
                .isInstanceOf(MemberNotFoundException.class);

        verify(memberDomainRepository).findById(memberId);
    }

    @Test
    @DisplayName("마케팅 동의 상태 변경 성공")
    void updateMarketingAgreement_Success() {
        // given
        Long memberId = 1L;
        MemberId memberIdVO = MemberId.of(memberId);
        Member member = createMemberWithMarketingAgreement(false);
        Member savedMember = createMemberWithMarketingAgreement(true);

        when(memberDomainRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(memberDomainRepository.save(any(Member.class))).thenReturn(savedMember);

        // when
        MarketingAgreement result = marketingAgreementService.updateMarketingAgreement(memberIdVO, true);

        // then
        assertThat(result.isAgreed()).isTrue();
        verify(memberDomainRepository).findById(memberId);
        verify(memberDomainRepository).save(member);
    }

    @Test
    @DisplayName("존재하지 않는 회원의 마케팅 동의 상태 변경 시 예외 발생")
    void updateMarketingAgreement_MemberNotFound_ThrowsException() {
        // given
        Long memberId = 999L;
        MemberId memberIdVO = MemberId.of(memberId);

        when(memberDomainRepository.findById(memberId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> marketingAgreementService.updateMarketingAgreement(memberIdVO, true))
                .isInstanceOf(MemberNotFoundException.class);

        verify(memberDomainRepository).findById(memberId);
        verify(memberDomainRepository, never()).save(any());
    }

    @Test
    @DisplayName("마케팅 동의를 동일한 상태로 변경")
    void updateMarketingAgreement_SameState() {
        // given
        Long memberId = 1L;
        MemberId memberIdVO = MemberId.of(memberId);
        Member member = createMemberWithMarketingAgreement(true);

        when(memberDomainRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(memberDomainRepository.save(any(Member.class))).thenReturn(member);

        // when
        MarketingAgreement result = marketingAgreementService.updateMarketingAgreement(memberIdVO, true);

        // then
        assertThat(result.isAgreed()).isTrue();
        verify(memberDomainRepository).save(member);
    }

    private Member createMemberWithMarketingAgreement(boolean isAgreed) {
        Member member = Member.builder()
                .id(1L)
                .email("test@example.com")
                .nickname("테스트회원")
                .major("컴퓨터공학과")
                .school("테스트대학교")
                .build();
        member.setMarketingAgreement(isAgreed);
        return member;
    }
}
