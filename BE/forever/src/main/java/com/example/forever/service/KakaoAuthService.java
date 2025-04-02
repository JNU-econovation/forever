package com.example.forever.service;


import com.example.forever.common.auth.JwtTokenProvider;
import com.example.forever.common.feign.kakao.client.KakaoInfoClient;
import com.example.forever.common.feign.kakao.client.KakaoTokenClient;
import com.example.forever.common.feign.kakao.dto.KakaoAccessTokenResponse;
import com.example.forever.common.feign.kakao.dto.KakaoMemberInfoResponse;
import com.example.forever.domain.Member;
import com.example.forever.domain.VerificationCode;
import com.example.forever.dto.KakaoLoginResponse;
import com.example.forever.dto.member.LoginTokenResponse;
import com.example.forever.dto.member.SignUpRequest;
import com.example.forever.email.VerificationCodeRepository;
import com.example.forever.exception.auth.InvalidKakaoCodeException;
import com.example.forever.exception.auth.OnboardingRequiredException;
import com.example.forever.repository.MemberRepository;
import feign.FeignException.FeignClientException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;


@Service
@RequiredArgsConstructor
public class KakaoAuthService {

    private final KakaoTokenClient kakaoTokenClient;
    private final KakaoInfoClient kakaoInfoClient;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final VerificationCodeRepository verificationCodeRepository;

    private final String kakaoClientId = "36d643394b6e66e4c5e99bf19398541f";
    //private final String kakaoClientId = "97544952621589e082444154812d231c";

    @Transactional
    public KakaoLoginResponse kakaoLogin(String code, HttpServletResponse response) {
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

        // 3. 사용자 존재 확인
        Optional<Member> optionalMember = memberRepository.findByEmail(email);

        if (optionalMember.isEmpty()) {
            // 가입이 안 된 경우: 회원가입 유도를 위한 응답 반환
            return new KakaoLoginResponse(null, null, email);
        }

        Member member = optionalMember.get();

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
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(refreshTokenCookie);

        // 7. 회원 정보 응답
        return new KakaoLoginResponse(member.getNickname(), member.getMajor(), null);
    }

    @Transactional
    public void kakaoSignUp(SignUpRequest request, HttpServletResponse response){
        //verificationcode가 맞는지 확인
        //맞다면 회원가입 진행
        //아니라면 에러
        VerificationCode verificationCode = verificationCodeRepository.findByCode(request.verificationCode()).orElseThrow(
                () -> new IllegalArgumentException("인증번호가 일치하지 않습니다.")
        );

        Member member = memberRepository.save(Member.builder().email(verificationCode.getEmail()).nickname(request.name()).build());


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
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(refreshTokenCookie);
    }

}

