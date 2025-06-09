package com.example.forever.domain;

import com.example.forever.domain.member.MarketingAgreement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Member 엔티티의 마케팅 동의 기능 테스트")
class MemberMarketingAgreementTest {

    @Test
    @DisplayName("Member 생성 시 기본 마케팅 동의는 false")
    void createMember_DefaultMarketingAgreementIsFalse() {
        // given & when
        Member member = Member.builder()
                .email("test@example.com")
                .nickname("테스트")
                .build();

        // then
        assertThat(member.getMarketingAgreement()).isNotNull();
        assertThat(member.getMarketingAgreement().isAgreed()).isFalse();
    }

    @Test
    @DisplayName("회원가입 시 마케팅 동의 설정")
    void setMarketingAgreement_OnSignup() {
        // given
        Member member = Member.builder()
                .email("test@example.com")
                .nickname("테스트")
                .build();

        // when
        member.setMarketingAgreement(true);

        // then
        assertThat(member.getMarketingAgreement().isAgreed()).isTrue();
        assertThat(member.getMarketingAgreement().getAgreementDate()).isBefore(LocalDateTime.now().plusSeconds(1));
    }

    @Test
    @DisplayName("마케팅 동의 상태 변경")
    void updateMarketingAgreement() {
        // given
        Member member = Member.builder()
                .email("test@example.com")
                .nickname("테스트")
                .build();
        member.setMarketingAgreement(false);
        LocalDateTime originalDate = member.getMarketingAgreement().getAgreementDate();

        // when
        member.updateMarketingAgreement(true);

        // then
        assertThat(member.getMarketingAgreement().isAgreed()).isTrue();
        assertThat(member.getMarketingAgreement().getAgreementDate()).isAfter(originalDate);
    }

    @Test
    @DisplayName("마케팅 동의 상태를 같은 값으로 변경해도 정상 동작")
    void updateMarketingAgreement_SameValue() {
        // given
        Member member = Member.builder()
                .email("test@example.com")
                .nickname("테스트")
                .build();
        member.setMarketingAgreement(true);
        MarketingAgreement originalAgreement = member.getMarketingAgreement();

        // when
        member.updateMarketingAgreement(true);

        // then
        assertThat(member.getMarketingAgreement()).isSameAs(originalAgreement);
    }

    @Test
    @DisplayName("마케팅 동의 여러 번 변경 테스트")
    void updateMarketingAgreement_MultipleTimes() {
        // given
        Member member = Member.builder()
                .email("test@example.com")
                .nickname("테스트")
                .build();

        // when & then
        member.setMarketingAgreement(true);
        assertThat(member.getMarketingAgreement().isAgreed()).isTrue();

        member.updateMarketingAgreement(false);
        assertThat(member.getMarketingAgreement().isAgreed()).isFalse();

        member.updateMarketingAgreement(true);
        assertThat(member.getMarketingAgreement().isAgreed()).isTrue();
    }
}
