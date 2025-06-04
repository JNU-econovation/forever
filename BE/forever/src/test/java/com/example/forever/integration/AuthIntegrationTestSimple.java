package com.example.forever.integration;

import com.example.forever.config.TestConfig;
import com.example.forever.domain.Member;
import com.example.forever.dto.member.SignUpRequest;
import com.example.forever.fixture.MemberFixture;
import com.example.forever.helper.ApiTestHelper;
import com.example.forever.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureWebMvc
@ActiveProfiles("test")
@Import(TestConfig.class)
@Transactional
@DisplayName("회원가입 통합 테스트")
class AuthIntegrationTestSimple {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("정상적인 회원가입 요청이 성공한다")
    void signUp_ValidRequest_Success() throws Exception {
        // given
        SignUpRequest request = MemberFixture.createValidSignUpRequest();

        // when
        ApiTestHelper.performSignUpRequest(mockMvc, objectMapper, request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().exists("Authorization"))
                .andExpect(cookie().exists("refresh_token"));

        // then
        Optional<Member> savedMember = memberRepository.findByEmail(request.email());
        assertThat(savedMember).isPresent();
        verifyMemberData(savedMember.get(), request);
    }

    @Test
    @DisplayName("다양한 유입경로로 회원가입이 성공한다")
    void signUp_VariousInflowSources_Success() throws Exception {
        // given
        List<String> inflowSources = List.of("광고", "SNS", "지인추천", "검색", "기타");
        SignUpRequest request = MemberFixture.createSignUpRequestWithInflow(inflowSources);

        // when
        ApiTestHelper.performSignUpRequest(mockMvc, objectMapper, request)
                .andExpect(status().isOk());

        // then
        Optional<Member> savedMember = memberRepository.findByEmail(request.email());
        assertThat(savedMember).isPresent();
        assertThat(savedMember.get().getInflow()).isEqualTo("광고,SNS,지인추천,검색,기타");
    }

    @Test
    @DisplayName("유입경로가 null인 경우에도 회원가입이 성공한다")
    void signUp_NullInflow_Success() throws Exception {
        // given
        SignUpRequest request = MemberFixture.createSignUpRequestWithInflow(null);

        // when
        ApiTestHelper.performSignUpRequest(mockMvc, objectMapper, request)
                .andExpect(status().isOk());

        // then
        Optional<Member> savedMember = memberRepository.findByEmail(request.email());
        assertThat(savedMember).isPresent();
        assertThat(savedMember.get().getInflow()).isNull();
    }

    @Test
    @DisplayName("빈 유입경로 리스트인 경우에도 회원가입이 성공한다")
    void signUp_EmptyInflowList_Success() throws Exception {
        // given
        SignUpRequest request = MemberFixture.createSignUpRequestWithInflow(List.of());

        // when
        ApiTestHelper.performSignUpRequest(mockMvc, objectMapper, request)
                .andExpect(status().isOk());

        // then
        Optional<Member> savedMember = memberRepository.findByEmail(request.email());
        assertThat(savedMember).isPresent();
        assertThat(savedMember.get().getInflow()).isEmpty();
    }

    @Test
    @DisplayName("회원가입 후 JWT 토큰이 올바른 형식으로 발급된다")
    void signUp_JwtTokenFormat_Valid() throws Exception {
        // given
        SignUpRequest request = MemberFixture.createSignUpRequestWithEmail("jwt@example.com");

        // when
        var result = ApiTestHelper.performSignUpRequest(mockMvc, objectMapper, request)
                .andExpect(status().isOk())
                .andReturn();

        // then
        String authHeader = result.getResponse().getHeader("Authorization");
        assertThat(authHeader).startsWith("Bearer ");
        
        String token = authHeader.substring(7);
        assertThat(token.split("\\.")).hasSize(3); // JWT는 header.payload.signature
    }

    @Test
    @DisplayName("회원가입 시 기본값들이 올바르게 설정된다")
    void signUp_DefaultValues_SetCorrectly() throws Exception {
        // given
        SignUpRequest request = MemberFixture.createSignUpRequestWithEmail("defaults@example.com");

        // when
        ApiTestHelper.performSignUpRequest(mockMvc, objectMapper, request)
                .andExpect(status().isOk());

        // then
        Optional<Member> savedMember = memberRepository.findByEmail("defaults@example.com");
        assertThat(savedMember).isPresent();
        
        Member member = savedMember.get();
        assertThat(member.getAvailableTokens()).isEqualTo(3); // 기본값 3으로 변경
        assertThat(member.isDeleted()).isFalse();
        assertThat(member.getDeletedAt()).isNull();
        assertThat(member.getIsAgreedPolicy()).isTrue();
        assertThat(member.getIsAgreedTerms()).isTrue();
        assertThat(member.getCreatedAt()).isNotNull();
        assertThat(member.getUpdatedAt()).isNotNull();
        assertThat(member.getRefreshToken()).isNotNull();
    }

    @Test
    @DisplayName("중복 이메일로 회원가입 시 실패한다")
    void signUp_DuplicateEmail_Fail() throws Exception {
        // given - 기존 회원 생성
        Member existingMember = MemberFixture.createMemberWithEmail("duplicate@example.com");
        memberRepository.save(existingMember);

        SignUpRequest request = MemberFixture.createSignUpRequestWithEmail("duplicate@example.com");

        // when & then
        ApiTestHelper.performSignUpRequest(mockMvc, objectMapper, request)
                .andDo(print())
                .andExpect(status().isBadRequest()); // 400 Bad Request로 변경

        // 기존 회원만 존재하는지 확인
        List<Member> members = memberRepository.findAll();
        assertThat(members).hasSize(1);
        assertThat(members.get(0).getNickname()).isEqualTo("테스트회원"); // 기존 회원
    }

    @Test
    @DisplayName("잘못된 JSON 형식인 경우 Bad Request를 반환한다")
    void signUp_InvalidJson_BadRequest() throws Exception {
        // given
        String invalidJson = "{ invalid json format }";

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/oauth/kakao/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    /**
     * 회원 데이터 검증 헬퍼 메서드
     */
    private void verifyMemberData(Member member, SignUpRequest request) {
        assertThat(member.getNickname()).isEqualTo(request.name());
        assertThat(member.getMajor()).isEqualTo(request.major());
        assertThat(member.getSchool()).isEqualTo(request.school());
        assertThat(member.getEmail()).isEqualTo(request.email());
        
        if (request.inflow() != null && !request.inflow().isEmpty()) {
            assertThat(member.getInflow()).isEqualTo(String.join(",", request.inflow()));
        } else if (request.inflow() != null && request.inflow().isEmpty()) {
            assertThat(member.getInflow()).isEmpty();
        } else {
            assertThat(member.getInflow()).isNull();
        }
    }
}
