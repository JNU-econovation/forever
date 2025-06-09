package com.example.forever.integration;

import com.example.forever.application.member.MarketingAgreementApplicationService;
import com.example.forever.application.member.MemberApplicationService;
import com.example.forever.application.member.SignUpCommand;
import com.example.forever.domain.Member;
import com.example.forever.dto.member.MarketingAgreementResponse;
import com.example.forever.dto.member.MarketingAgreementUpdateRequest;
import com.example.forever.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@DisplayName("마케팅 동의 통합 테스트")
class MarketingAgreementIntegrationTest {

    @Autowired
    private MarketingAgreementApplicationService marketingAgreementApplicationService;

    @Autowired
    private MemberApplicationService memberApplicationService;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입 시 마케팅 동의 포함하여 저장")
    void signUpWithMarketingAgreement() {
        // given
        SignUpCommand command = new SignUpCommand(
                "곽민주",
                "소프트웨어공학과",
                "전남대학교",
                "rootachieve@gmail.com",
                List.of("지인추천", "기타"),
                true
        );
        MockHttpServletResponse response = new MockHttpServletResponse();

        // when
        memberApplicationService.signUp(command, response);

        // then
        Optional<Member> savedMember = memberRepository.findByEmail("rootachieve@gmail.com");
        assertThat(savedMember).isPresent();
        assertThat(savedMember.get().getMarketingAgreement().isAgreed()).isTrue();
        assertThat(savedMember.get().getMarketingAgreement().getAgreementDate()).isNotNull();
    }

    @Test
    @DisplayName("회원가입 시 마케팅 동의하지 않음으로 저장")
    void signUpWithoutMarketingAgreement() {
        // given
        SignUpCommand command = new SignUpCommand(
                "테스트유저",
                "컴퓨터공학과",
                "테스트대학교",
                "test@example.com",
                List.of("검색"),
                false
        );
        MockHttpServletResponse response = new MockHttpServletResponse();

        // when
        memberApplicationService.signUp(command, response);

        // then
        Optional<Member> savedMember = memberRepository.findByEmail("test@example.com");
        assertThat(savedMember).isPresent();
        assertThat(savedMember.get().getMarketingAgreement().isAgreed()).isFalse();
    }

    @Test
    @DisplayName("회원가입 시 마케팅 동의 null인 경우 기본값 false로 저장")
    void signUpWithNullMarketingAgreement() {
        // given
        SignUpCommand command = new SignUpCommand(
                "널테스트",
                "컴퓨터공학과",
                "테스트대학교",
                "null@example.com",
                List.of("기타"),
                null
        );
        MockHttpServletResponse response = new MockHttpServletResponse();

        // when
        memberApplicationService.signUp(command, response);

        // then
        Optional<Member> savedMember = memberRepository.findByEmail("null@example.com");
        assertThat(savedMember).isPresent();
        assertThat(savedMember.get().getMarketingAgreement().isAgreed()).isFalse();
    }

    @Test
    @DisplayName("마케팅 동의 정보 조회 통합 테스트")
    void getMarketingAgreement_Integration() {
        // given
        Member member = createAndSaveMember("get@example.com", true);

        // when
        MarketingAgreementResponse response = marketingAgreementApplicationService
                .getMarketingAgreement(member.getId());

        // then
        assertThat(response.isAgreement()).isTrue();
        assertThat(response.effectiveDate()).isNotNull();
        assertThat(response.effectiveDate()).matches("\\d{2}-\\d{2}-\\d{2}"); // yy-MM-dd 형식 확인
    }

    @Test
    @DisplayName("마케팅 동의 상태 변경 통합 테스트")
    void updateMarketingAgreement_Integration() {
        // given
        Member member = createAndSaveMember("update@example.com", false);
        MarketingAgreementUpdateRequest request = new MarketingAgreementUpdateRequest(true);

        // when
        MarketingAgreementResponse response = marketingAgreementApplicationService
                .updateMarketingAgreement(member.getId(), request);

        // then
        assertThat(response.isAgreement()).isTrue();
        assertThat(response.effectiveDate()).isNotNull();

        // 데이터베이스에서 실제로 변경되었는지 확인
        Member updatedMember = memberRepository.findById(member.getId()).orElseThrow();
        assertThat(updatedMember.getMarketingAgreement().isAgreed()).isTrue();
    }

    @Test
    @DisplayName("마케팅 동의 상태를 여러 번 변경하는 통합 테스트")
    void updateMarketingAgreement_MultipleTimes_Integration() {
        // given
        Member member = createAndSaveMember("multiple@example.com", false);

        // when & then
        // 1. false -> true
        MarketingAgreementUpdateRequest requestTrue = new MarketingAgreementUpdateRequest(true);
        MarketingAgreementResponse responseTrue = marketingAgreementApplicationService
                .updateMarketingAgreement(member.getId(), requestTrue);
        assertThat(responseTrue.isAgreement()).isTrue();

        // 2. true -> false
        MarketingAgreementUpdateRequest requestFalse = new MarketingAgreementUpdateRequest(false);
        MarketingAgreementResponse responseFalse = marketingAgreementApplicationService
                .updateMarketingAgreement(member.getId(), requestFalse);
        assertThat(responseFalse.isAgreement()).isFalse();

        // 3. 최종 상태 확인
        MarketingAgreementResponse finalResponse = marketingAgreementApplicationService
                .getMarketingAgreement(member.getId());
        assertThat(finalResponse.isAgreement()).isFalse();
    }

    @Test
    @DisplayName("동일한 상태로 마케팅 동의 변경 시 날짜는 그대로 유지")
    void updateMarketingAgreement_SameState_DateUnchanged() {
        // given
        Member member = createAndSaveMember("same@example.com", true);
        MarketingAgreementResponse originalResponse = marketingAgreementApplicationService
                .getMarketingAgreement(member.getId());

        // when
        MarketingAgreementUpdateRequest request = new MarketingAgreementUpdateRequest(true);
        MarketingAgreementResponse updatedResponse = marketingAgreementApplicationService
                .updateMarketingAgreement(member.getId(), request);

        // then
        assertThat(updatedResponse.isAgreement()).isTrue();
        assertThat(updatedResponse.effectiveDate()).isEqualTo(originalResponse.effectiveDate());
    }

    private Member createAndSaveMember(String email, boolean marketingAgreement) {
        Member member = Member.builder()
                .email(email)
                .nickname("테스트회원")
                .major("컴퓨터공학과")
                .school("테스트대학교")
                .inflow("테스트")
                .build();
        member.setMarketingAgreement(marketingAgreement);
        return memberRepository.save(member);
    }
}
