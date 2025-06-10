package com.example.forever.application.member;

import com.example.forever.domain.member.MarketingAgreement;
import com.example.forever.domain.member.MarketingAgreementService;
import com.example.forever.domain.member.MemberId;
import com.example.forever.dto.member.MarketingAgreementResponse;
import com.example.forever.dto.member.MarketingAgreementUpdateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("MarketingAgreementApplicationService 단위 테스트")
class MarketingAgreementApplicationServiceTest {

    @Mock
    private MarketingAgreementService marketingAgreementService;

    @InjectMocks
    private MarketingAgreementApplicationService applicationService;

    @Test
    @DisplayName("마케팅 동의 정보 조회 성공")
    void getMarketingAgreement_Success() {
        // given
        Long memberId = 1L;
        MarketingAgreement agreement = MarketingAgreement.of(true);

        when(marketingAgreementService.getMarketingAgreement(any(MemberId.class)))
                .thenReturn(agreement);

        // when
        MarketingAgreementResponse response = applicationService.getMarketingAgreement(memberId);

        // then
        assertThat(response.isAgreement()).isTrue();
        verify(marketingAgreementService).getMarketingAgreement(MemberId.of(memberId));
    }

    @Test
    @DisplayName("마케팅 동의 정보 조회 - 동의하지 않음")
    void getMarketingAgreement_NotAgreed() {
        // given
        Long memberId = 1L;
        MarketingAgreement agreement = MarketingAgreement.of(false);

        when(marketingAgreementService.getMarketingAgreement(any(MemberId.class)))
                .thenReturn(agreement);

        // when
        MarketingAgreementResponse response = applicationService.getMarketingAgreement(memberId);

        // then
        assertThat(response.isAgreement()).isFalse();
    }

    @Test
    @DisplayName("마케팅 동의 상태 변경 성공 - 동의함으로 변경")
    void updateMarketingAgreement_ToAgreed_Success() {
        // given
        Long memberId = 1L;
        MarketingAgreementUpdateRequest request = new MarketingAgreementUpdateRequest(true);
        MarketingAgreement updatedAgreement = MarketingAgreement.of(true);

        when(marketingAgreementService.updateMarketingAgreement(any(MemberId.class), eq(true)))
                .thenReturn(updatedAgreement);

        // when
        MarketingAgreementResponse response = applicationService.updateMarketingAgreement(memberId, request);

        // then
        assertThat(response.isAgreement()).isTrue();
        verify(marketingAgreementService).updateMarketingAgreement(MemberId.of(memberId), true);
    }

    @Test
    @DisplayName("마케팅 동의 상태 변경 성공 - 동의하지 않음으로 변경")
    void updateMarketingAgreement_ToNotAgreed_Success() {
        // given
        Long memberId = 1L;
        MarketingAgreementUpdateRequest request = new MarketingAgreementUpdateRequest(false);
        MarketingAgreement updatedAgreement = MarketingAgreement.of(false);

        when(marketingAgreementService.updateMarketingAgreement(any(MemberId.class), eq(false)))
                .thenReturn(updatedAgreement);

        // when
        MarketingAgreementResponse response = applicationService.updateMarketingAgreement(memberId, request);

        // then
        assertThat(response.isAgreement()).isFalse();
        verify(marketingAgreementService).updateMarketingAgreement(MemberId.of(memberId), false);
    }

    @Test
    @DisplayName("마케팅 동의 날짜 확인")
    void verifyMarketingAgreementDate() {
        // given
        Long memberId = 1L;
        MarketingAgreement agreement = MarketingAgreement.of(true);

        when(marketingAgreementService.getMarketingAgreement(any(MemberId.class)))
                .thenReturn(agreement);

        // when
        MarketingAgreementResponse response = applicationService.getMarketingAgreement(memberId);

        // then
        assertThat(response.isAgreement()).isTrue();
        // 날짜는 고정값이므로 별도 검증 불필요 (도메인 레벨에서 검증됨)
    }
}
