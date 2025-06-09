package com.example.forever.integration;

import com.example.forever.application.token.TokenUsageApplicationService;
import com.example.forever.domain.Member;
import com.example.forever.dto.member.TokenUsageResponse;
import com.example.forever.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@DisplayName("토큰 사용량 통합 테스트")
class TokenUsageIntegrationTest {

    @Autowired
    private TokenUsageApplicationService tokenUsageApplicationService;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("토큰 사용량 조회 통합 테스트 - 정상 케이스")
    void getTokenUsage_Integration_Success() {
        // given
        Member member = createAndSaveMember("usage@example.com", 5);

        // when
        TokenUsageResponse response = tokenUsageApplicationService.getRemainingUsage(member.getId());

        // then
        assertThat(response.remainUsage()).isEqualTo(5);
    }

    @Test
    @DisplayName("토큰 사용량 조회 통합 테스트 - 자동 갱신")
    void getTokenUsage_Integration_AutoRefresh() {
        // given - 어제 날짜로 설정하여 갱신이 필요하도록 함
        Member member = createMemberWithExpiredTokens("expired@example.com");
        Member savedMember = memberRepository.save(member);

        // when
        TokenUsageResponse response = tokenUsageApplicationService.getRemainingUsage(savedMember.getId());

        // then
        assertThat(response.remainUsage()).isEqualTo(3); // 기본 토큰 개수

        // 데이터베이스에서 실제로 갱신되었는지 확인
        Member updatedMember = memberRepository.findById(savedMember.getId()).orElseThrow();
        assertThat(updatedMember.getAvailableTokens()).isEqualTo(3);
        assertThat(updatedMember.getLastTokenRefreshDate()).isEqualTo(LocalDate.now());
    }

    @Test
    @DisplayName("토큰 사용량 조회 통합 테스트 - 0개")
    void getTokenUsage_Integration_ZeroTokens() {
        // given
        Member member = createAndSaveMember("zero@example.com", 0);

        // when
        TokenUsageResponse response = tokenUsageApplicationService.getRemainingUsage(member.getId());

        // then
        assertThat(response.remainUsage()).isEqualTo(0);
    }

    @Test
    @DisplayName("토큰 사용량 조회 통합 테스트 - 갱신 불필요")
    void getTokenUsage_Integration_NoRefreshNeeded() {
        // given
        Member member = createAndSaveMember("current@example.com", 2);
        
        // when
        TokenUsageResponse response = tokenUsageApplicationService.getRemainingUsage(member.getId());

        // then
        assertThat(response.remainUsage()).isEqualTo(2);

        // 갱신되지 않았는지 확인
        Member unchangedMember = memberRepository.findById(member.getId()).orElseThrow();
        assertThat(unchangedMember.getAvailableTokens()).isEqualTo(2);
        assertThat(unchangedMember.getLastTokenRefreshDate()).isEqualTo(LocalDate.now());
    }

    @Test
    @DisplayName("토큰 사용량 조회 통합 테스트 - 여러 회원")
    void getTokenUsage_Integration_MultipleMembers() {
        // given
        Member member1 = createAndSaveMember("member1@example.com", 3);
        Member member2 = createAndSaveMember("member2@example.com", 1);
        Member member3 = createAndSaveMember("member3@example.com", 0);

        // when & then
        TokenUsageResponse response1 = tokenUsageApplicationService.getRemainingUsage(member1.getId());
        assertThat(response1.remainUsage()).isEqualTo(3);

        TokenUsageResponse response2 = tokenUsageApplicationService.getRemainingUsage(member2.getId());
        assertThat(response2.remainUsage()).isEqualTo(1);

        TokenUsageResponse response3 = tokenUsageApplicationService.getRemainingUsage(member3.getId());
        assertThat(response3.remainUsage()).isEqualTo(0);
    }

    @Test
    @DisplayName("토큰 사용 후 사용량 조회 통합 테스트")
    void getTokenUsage_Integration_AfterTokenUsage() {
        // given
        Member member = createAndSaveMember("usage@example.com", 3);

        // when - 토큰 사용
        member.useToken();
        memberRepository.save(member);

        // then - 사용량 조회
        TokenUsageResponse response = tokenUsageApplicationService.getRemainingUsage(member.getId());
        assertThat(response.remainUsage()).isEqualTo(2);
    }

    private Member createAndSaveMember(String email, int availableTokens) {
        Member member = Member.builder()
                .email(email)
                .nickname("테스트회원")
                .major("컴퓨터공학과")
                .school("테스트대학교")
                .inflow("테스트")
                .availableTokens(availableTokens)
                .lastTokenRefreshDate(LocalDate.now())
                .build();
        return memberRepository.save(member);
    }

    private Member createMemberWithExpiredTokens(String email) {
        return Member.builder()
                .email(email)
                .nickname("테스트회원")
                .major("컴퓨터공학과")
                .school("테스트대학교")
                .inflow("테스트")
                .availableTokens(0)
                .lastTokenRefreshDate(LocalDate.now().minusDays(1)) // 어제 날짜
                .build();
    }
}
