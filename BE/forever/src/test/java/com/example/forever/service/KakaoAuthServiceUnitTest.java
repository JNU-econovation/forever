package com.example.forever.service;

import com.example.forever.application.member.MemberApplicationService;
import com.example.forever.application.member.SignUpCommand;
import com.example.forever.domain.Member;
import com.example.forever.repository.MemberRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@DisplayName("MemberApplicationService 통합 테스트")
class MemberApplicationServiceIntegrationTest {

    @Autowired
    private MemberApplicationService memberApplicationService;

    @Autowired
    private MemberRepository memberRepository;

    @MockBean
    private HttpServletResponse httpServletResponse;

    @Test
    @DisplayName("회원가입 시 Member가 올바르게 생성되고 저장된다")
    void signUp_MemberCreatedAndSaved() {
        // given
        SignUpCommand command = new SignUpCommand(
                "곽민주",
                "소프트웨어공학과", 
                "전남대학교",
                "rootachieve@gmail.com",
                List.of("지인추천", "기타"),
                true
        );

        // when
        memberApplicationService.signUp(command, httpServletResponse);

        // then
        Member savedMember = memberRepository.findByEmail(command.email()).orElse(null);
        assertThat(savedMember).isNotNull();
        assertThat(savedMember.getEmail()).isEqualTo(command.email());
        assertThat(savedMember.getNickname()).isEqualTo(command.name());
        assertThat(savedMember.getMajor()).isEqualTo(command.major());
        assertThat(savedMember.getSchool()).isEqualTo(command.school());
        assertThat(savedMember.getInflow()).isEqualTo("지인추천,기타");
    }

    @Test
    @DisplayName("inflow가 null인 경우 null로 저장된다")
    void signUp_NullInflow_SavedAsNull() {
        // given
        SignUpCommand command = new SignUpCommand(
                "null테스트",
                "null학과",
                "null대학교",
                "null@example.com",
                null,
                false
        );

        // when
        memberApplicationService.signUp(command, httpServletResponse);

        // then
        Member savedMember = memberRepository.findByEmail(command.email()).orElse(null);
        assertThat(savedMember).isNotNull();
        assertThat(savedMember.getInflow()).isNull();
    }

    @Test
    @DisplayName("빈 inflow 리스트인 경우 빈 문자열로 저장된다")
    void signUp_EmptyInflowList_SavedAsEmptyString() {
        // given
        SignUpCommand command = new SignUpCommand(
                "빈리스트테스트",
                "빈리스트학과",
                "빈리스트대학교",
                "empty@example.com",
                List.of(),
                null
        );

        // when
        memberApplicationService.signUp(command, httpServletResponse);

        // then
        Member savedMember = memberRepository.findByEmail(command.email()).orElse(null);
        assertThat(savedMember).isNotNull();
        assertThat(savedMember.getInflow()).isEmpty();
    }
}
