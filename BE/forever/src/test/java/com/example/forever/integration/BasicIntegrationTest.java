package com.example.forever.integration;

import com.example.forever.domain.Member;
import com.example.forever.repository.MemberRepository;
import com.example.forever.application.member.MemberApplicationService;
import com.example.forever.application.member.SignUpCommand;
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
@DisplayName("멤버 통합 테스트")
class BasicIntegrationTest {

    @Autowired
    private MemberApplicationService memberApplicationService;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입이 정상 동작한다 - inflow 포함")
    void signUp_WithInflow_Success() {
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
        
        Member member = savedMember.get();
        assertThat(member.getNickname()).isEqualTo("곽민주");
        assertThat(member.getMajor()).isEqualTo("소프트웨어공학과");
        assertThat(member.getSchool()).isEqualTo("전남대학교");
        assertThat(member.getInflow()).isEqualTo("지인추천,기타");
        
        // JWT 토큰 검증
        assertThat(response.getHeader("Authorization")).isNotNull();
        assertThat(response.getHeader("Authorization")).startsWith("Bearer ");
        
        // 쿠키 검증
        assertThat(response.getCookies()).hasSize(1);
        assertThat(response.getCookies()[0].getName()).isEqualTo("refresh_token");
    }

    @Test
    @DisplayName("회원가입이 정상 동작한다 - inflow null")
    void signUp_WithNullInflow_Success() {
        // given
        SignUpCommand command = new SignUpCommand(
                "테스트유저",
                "컴퓨터공학과",
                "테스트대학교",
                "test@example.com",
                null,
                false
        );
        
        MockHttpServletResponse response = new MockHttpServletResponse();

        // when
        memberApplicationService.signUp(command, response);

        // then
        Optional<Member> savedMember = memberRepository.findByEmail("test@example.com");
        assertThat(savedMember).isPresent();
        assertThat(savedMember.get().getInflow()).isNull();
    }

    @Test
    @DisplayName("회원가입이 정상 동작한다 - inflow 빈 리스트")
    void signUp_WithEmptyInflow_Success() {
        // given
        SignUpCommand command = new SignUpCommand(
                "빈리스트유저",
                "빈리스트학과",
                "빈리스트대학교",
                "empty@example.com",
                List.of(),
                null
        );
        
        MockHttpServletResponse response = new MockHttpServletResponse();

        // when
        memberApplicationService.signUp(command, response);

        // then
        Optional<Member> savedMember = memberRepository.findByEmail("empty@example.com");
        assertThat(savedMember).isPresent();
        assertThat(savedMember.get().getInflow()).isEmpty();
    }
}
