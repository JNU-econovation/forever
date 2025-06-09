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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yy-MM-dd");

    @Test
    @DisplayName("마케팅 동의 정보 조회 성공")
    void getMarketingAgreement_Success() {
        // given
        Long memberId = 1L;
        LocalDateTime agreementDate = LocalDateTime.of(2025, 6, 8, 12, 0);
        MarketingAgreement agreement = MarketingAgreement.of(true);

        when(marketingAgreementService.getMarketingAgreement(any(MemberId.class)))
                .thenReturn(createMarketingAgreementWithDate(true, agreementDate));

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
        LocalDateTime agreementDate = LocalDateTime.of(2025, 6, 8, 15, 30);

        when(marketingAgreementService.getMarketingAgreement(any(MemberId.class)))
                .thenReturn(createMarketingAgreementWithDate(false, agreementDate));

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
        LocalDateTime updatedDate = LocalDateTime.of(2025, 6, 8, 16, 45);

        when(marketingAgreementService.updateMarketingAgreement(any(MemberId.class), eq(true)))
                .thenReturn(createMarketingAgreementWithDate(true, updatedDate));

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
        LocalDateTime updatedDate = LocalDateTime.of(2025, 12, 25, 10, 30);

        when(marketingAgreementService.updateMarketingAgreement(any(MemberId.class), eq(false)))
                .thenReturn(createMarketingAgreementWithDate(false, updatedDate));

        // when
        MarketingAgreementResponse response = applicationService.updateMarketingAgreement(memberId, request);

        // then
        assertThat(response.isAgreement()).isFalse();
        verify(marketingAgreementService).updateMarketingAgreement(MemberId.of(memberId), false);
    }

    /**
     * 테스트용 MarketingAgreement 생성 (특정 날짜 포함)
     */
    private MarketingAgreement createMarketingAgreementWithDate(boolean isAgreed, LocalDateTime date) {
        MarketingAgreement mockAgreement = MarketingAgreement.of(isAgreed);
        // 단순화를 위해 현재 시간 사용
        return mockAgreement;
    }
}
