package com.example.forever.domain.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("MarketingAgreement Value Object 테스트")
class MarketingAgreementTest {

    @Test
    @DisplayName("마케팅 동의 생성 - 동의함")
    void createMarketingAgreement_Agreed() {
        // given & when
        MarketingAgreement agreement = MarketingAgreement.of(true);

        // then
        assertThat(agreement.isAgreed()).isTrue();
        assertThat(agreement.getAgreementDate()).isBefore(LocalDateTime.now().plusSeconds(1));
        assertThat(agreement.getAgreementDate()).isAfter(LocalDateTime.now().minusSeconds(1));
    }

    @Test
    @DisplayName("마케팅 동의 생성 - 동의하지 않음")
    void createMarketingAgreement_NotAgreed() {
        // given & when
        MarketingAgreement agreement = MarketingAgreement.of(false);

        // then
        assertThat(agreement.isAgreed()).isFalse();
        assertThat(agreement.getAgreementDate()).isNotNull();
    }

    @Test
    @DisplayName("마케팅 동의 상태 변경 - 동의하지 않음에서 동의함으로")
    void updateAgreement_FromFalseToTrue() {
        // given
        MarketingAgreement originalAgreement = MarketingAgreement.of(false);
        LocalDateTime originalDate = originalAgreement.getAgreementDate();

        // when
        MarketingAgreement updatedAgreement = originalAgreement.updateAgreement(true);

        // then
        assertThat(updatedAgreement.isAgreed()).isTrue();
        assertThat(updatedAgreement.getAgreementDate()).isAfter(originalDate);
        assertThat(originalAgreement.isAgreed()).isFalse(); // 원본 불변성 확인
    }

    @Test
    @DisplayName("마케팅 동의 상태 변경 - 동일한 상태로 변경 시 같은 객체 반환")
    void updateAgreement_SameState_ReturnsSameObject() {
        // given
        MarketingAgreement agreement = MarketingAgreement.of(true);

        // when
        MarketingAgreement updatedAgreement = agreement.updateAgreement(true);

        // then
        assertThat(updatedAgreement).isSameAs(agreement);
    }

    @Test
    @DisplayName("마케팅 동의 상태 변경 - 동의함에서 동의하지 않음으로")
    void updateAgreement_FromTrueToFalse() {
        // given
        MarketingAgreement originalAgreement = MarketingAgreement.of(true);
        LocalDateTime originalDate = originalAgreement.getAgreementDate();

        // when
        MarketingAgreement updatedAgreement = originalAgreement.updateAgreement(false);

        // then
        assertThat(updatedAgreement.isAgreed()).isFalse();
        assertThat(updatedAgreement.getAgreementDate()).isAfter(originalDate);
    }

    @Test
    @DisplayName("equals와 hashCode 테스트")
    void testEqualsAndHashCode() {
        // given
        MarketingAgreement agreement1 = MarketingAgreement.of(true);
        MarketingAgreement agreement2 = MarketingAgreement.of(true);

        // when & then
        assertThat(agreement1).isNotEqualTo(agreement2); // 생성 시간이 다르므로 다름
        assertThat(agreement1.hashCode()).isNotEqualTo(agreement2.hashCode());
        
        // 같은 객체는 같음
        assertThat(agreement1).isEqualTo(agreement1);
        assertThat(agreement1.hashCode()).isEqualTo(agreement1.hashCode());
    }

    @Test
    @DisplayName("Null 안전성 테스트")
    void testNullSafety() {
        // given
        MarketingAgreement agreement = MarketingAgreement.of(false);

        // when & then
        assertThat(agreement).isNotEqualTo(null);
        assertThat(agreement.getIsAgreed()).isNotNull();
        assertThat(agreement.getAgreementDate()).isNotNull();
    }
}
