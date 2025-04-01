package com.example.forever.service;


import com.example.forever.common.auth.JwtTokenProvider;
import com.example.forever.common.feign.kakao.client.KakaoInfoClient;
import com.example.forever.common.feign.kakao.client.KakaoTokenClient;
import com.example.forever.common.feign.kakao.dto.KakaoAccessTokenResponse;
import com.example.forever.common.feign.kakao.dto.KakaoMemberInfoResponse;
import com.example.forever.domain.Member;
import com.example.forever.dto.member.LoginTokenResponse;
import com.example.forever.exception.auth.InvalidKakaoCodeException;
import com.example.forever.exception.auth.OnboardingRequiredException;
import com.example.forever.repository.MemberRepository;
import feign.FeignException.FeignClientException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class KakaoAuthService {

    private final KakaoTokenClient kakaoTokenClient;
    private final KakaoInfoClient kakaoInfoClient;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    private final String kakaoClientId = "36d643394b6e66e4c5e99bf19398541f";

    @Transactional
    public void kakaoLogin(String code, HttpServletResponse response) {
        // 1. 카카오 토큰 요청
        KakaoMemberInfoResponse kakaoMemberInfoResponse = null;
        try {
            KakaoAccessTokenResponse kakaoToken = kakaoTokenClient.getAccessToken(
                    "authorization_code", kakaoClientId, code
            );
            // 2. 사용자 정보 요청
            String bearer = "Bearer " + kakaoToken.accessToken();
            kakaoMemberInfoResponse = kakaoInfoClient.getUserInfo(bearer);
        }catch (FeignClientException e){
            if (e.status() == 400 || e.status() == 401) {
                throw new InvalidKakaoCodeException();
            }
        }

        assert kakaoMemberInfoResponse != null;
        String email = kakaoMemberInfoResponse.kakaoAccount().email();
        String nickname = kakaoMemberInfoResponse.kakaoAccount().profile().nickname();

   //      3. 사용자 존재 확인
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(OnboardingRequiredException::new);

        //TODO : 전화번호 인증 나중에 추가
//        Member member = memberRepository.findByEmail(email)
//                .orElseGet(() -> memberRepository.save(Member.builder().email(email).nickname(nickname).build()));

        // 4. 토큰 발급
        String accessToken = jwtTokenProvider.createAccessToken(member.getId(), "member");
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getId());

        // 5. 리프레시 토큰 저장
        member.updateRefreshToken(refreshToken);
        memberRepository.save(member);

        // 6. 응답 헤더 설정
        response.setHeader("Authorization", "Bearer " + accessToken);

        Cookie refreshTokenCookie = new Cookie("refresh_token", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true); // HTTPS 환경에서만 전송
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60); // 7일 유지
        response.addCookie(refreshTokenCookie);

    }
}

