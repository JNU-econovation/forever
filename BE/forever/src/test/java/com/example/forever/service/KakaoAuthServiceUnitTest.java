package com.example.forever.service;

import com.example.forever.common.auth.JwtTokenProvider;
import com.example.forever.domain.Member;
import com.example.forever.dto.member.SignUpRequest;
import com.example.forever.repository.MemberRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("KakaoAuthService 단위 테스트")
class KakaoAuthServiceUnitTest {

    @InjectMocks
    private KakaoAuthService kakaoAuthService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private HttpServletResponse httpServletResponse;

    @Test
    @DisplayName("회원가입 시 Member가 올바르게 생성되고 저장된다")
    void kakaoSignUp_MemberCreatedAndSaved() {
        // given
        SignUpRequest request = new SignUpRequest(
                "곽민주",
                "소프트웨어공학과",
                "전남대학교",
                "rootachieve@gmail.com",
                List.of("지인추천", "기타")
        );

        String expectedAccessToken = "access_token_123";
        String expectedRefreshToken = "refresh_token_123";
        
        when(jwtTokenProvider.createAccessToken(any(), eq("member"))).thenReturn(expectedAccessToken);
        when(jwtTokenProvider.createRefreshToken(any())).thenReturn(expectedRefreshToken);
        
        Member savedMember = Member.builder()
                .id(1L)
                .email(request.email())
                .nickname(request.name())
                .major(request.major())
                .school(request.school())
                .inflow("지인추천,기타")
                .build();
        
        when(memberRepository.save(any(Member.class))).thenReturn(savedMember);

        // when
        kakaoAuthService.kakaoSignUp(request, httpServletResponse);

        // then
        ArgumentCaptor<Member> memberCaptor = ArgumentCaptor.forClass(Member.class);
        verify(memberRepository, times(2)).save(memberCaptor.capture()); // 첫 번째 저장, 두 번째 토큰 업데이트

        Member capturedMember = memberCaptor.getAllValues().get(0);
        assertThat(capturedMember.getEmail()).isEqualTo(request.email());
        assertThat(capturedMember.getNickname()).isEqualTo(request.name());
        assertThat(capturedMember.getMajor()).isEqualTo(request.major());
        assertThat(capturedMember.getSchool()).isEqualTo(request.school());
        assertThat(capturedMember.getInflow()).isEqualTo("지인추천,기타");
    }

    @Test
    @DisplayName("inflow가 null인 경우 null로 저장된다")
    void kakaoSignUp_NullInflow_SavedAsNull() {
        // given
        SignUpRequest request = new SignUpRequest(
                "null테스트",
                "null학과",
                "null대학교",
                "null@example.com",
                null
        );

        when(jwtTokenProvider.createAccessToken(any(), any())).thenReturn("access_token");
        when(jwtTokenProvider.createRefreshToken(any())).thenReturn("refresh_token");
        
        Member savedMember = Member.builder().id(1L).build();
        when(memberRepository.save(any(Member.class))).thenReturn(savedMember);

        // when
        kakaoAuthService.kakaoSignUp(request, httpServletResponse);

        // then
        ArgumentCaptor<Member> memberCaptor = ArgumentCaptor.forClass(Member.class);
        verify(memberRepository, times(2)).save(memberCaptor.capture());

        Member capturedMember = memberCaptor.getAllValues().get(0);
        assertThat(capturedMember.getInflow()).isNull();
    }

    @Test
    @DisplayName("빈 inflow 리스트인 경우 빈 문자열로 저장된다")
    void kakaoSignUp_EmptyInflowList_SavedAsEmptyString() {
        // given
        SignUpRequest request = new SignUpRequest(
                "빈리스트테스트",
                "빈리스트학과",
                "빈리스트대학교",
                "empty@example.com",
                List.of()
        );

        when(jwtTokenProvider.createAccessToken(any(), any())).thenReturn("access_token");
        when(jwtTokenProvider.createRefreshToken(any())).thenReturn("refresh_token");
        
        Member savedMember = Member.builder().id(1L).build();
        when(memberRepository.save(any(Member.class))).thenReturn(savedMember);

        // when
        kakaoAuthService.kakaoSignUp(request, httpServletResponse);

        // then
        ArgumentCaptor<Member> memberCaptor = ArgumentCaptor.forClass(Member.class);
        verify(memberRepository, times(2)).save(memberCaptor.capture());

        Member capturedMember = memberCaptor.getAllValues().get(0);
        assertThat(capturedMember.getInflow()).isEmpty();
    }
}
