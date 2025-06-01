package com.example.forever.service;


import com.example.forever.common.auth.JwtTokenProvider;
import com.example.forever.common.feign.kakao.client.KakaoInfoClient;
import com.example.forever.common.feign.kakao.client.KakaoTokenClient;
import com.example.forever.common.feign.kakao.client.KakaoUnlinkClient;
import com.example.forever.common.feign.kakao.dto.KakaoAccessTokenResponse;
import com.example.forever.common.feign.kakao.dto.KakaoMemberInfoResponse;
import com.example.forever.common.validator.MemberValidator;
import com.example.forever.domain.Member;
import com.example.forever.domain.VerificationCode;
import com.example.forever.dto.KakaoLoginResponse;
import com.example.forever.dto.member.SignUpRequest;
import com.example.forever.email.VerificationCodeRepository;
import com.example.forever.exception.auth.AlreadyExistsEmailException;
import com.example.forever.exception.auth.DeletedMemberException;
import com.example.forever.exception.auth.InvalidKakaoCodeException;
import com.example.forever.exception.auth.InvalidRefreshTokenException;
import com.example.forever.exception.auth.InvalidVerificationCode;
import com.example.forever.repository.MemberRepository;
import feign.FeignException.FeignClientException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
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
    private final VerificationCodeRepository verificationCodeRepository;
    private final MemberValidator memberValidator;
    private final KakaoUnlinkClient kakaoUnlinkClient;

    //TODO : 환경변수화
    private final String kakaoClientId = "36d643394b6e66e4c5e99bf19398541f";

    @Transactional
    public KakaoLoginResponse kakaoLogin(String code, HttpServletResponse response) {
        // 1. 카카오 토큰 요청
        KakaoMemberInfoResponse kakaoMemberInfoResponse = null;
        String bearerCode = null;
        try {
            KakaoAccessTokenResponse kakaoToken = kakaoTokenClient.getAccessToken(
                    "authorization_code", kakaoClientId, code
            );
            // 2. 사용자 정보 요청
            bearerCode = "Bearer " + kakaoToken.accessToken();
            kakaoMemberInfoResponse = kakaoInfoClient.getUserInfo(bearerCode);
        } catch (FeignClientException e) {
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
            return new KakaoLoginResponse(null, null, null, email);
        }

        Member member = optionalMember.get();

        //회원탈퇴 된 회원일 경우
        if (member.isDeleted()) {
            throw new DeletedMemberException();
        }

        // 4. 토큰 발급
        String accessToken = jwtTokenProvider.createAccessToken(member.getId(), "member");
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getId());

        // 5. 리프레시 토큰 저장
        member.updateRefreshToken(refreshToken);
        member.updateKakaoAccessToken(bearerCode);
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
        return new KakaoLoginResponse(member.getNickname(), member.getMajor(), member.getSchool(), null);
    }

    @Transactional
    public void kakaoSignUp(SignUpRequest request, HttpServletResponse response) {
        //verificationcode가 맞는지 확인
        //맞다면 회원가입 진행
        //아니라면 에러
//        VerificationCode verificationCode = verificationCodeRepository.findByCode(request.verificationCode())
//                .orElseThrow(
//                        InvalidVerificationCode::new
//                );

        // 이메일 중복 체크
        if (memberRepository.findByEmail(request.email()).isPresent()) {
            throw new AlreadyExistsEmailException();
        }

        // inflow 리스트를 문자열로 변환 (JSON 형태 또는 콤마로 구분)
        String inflowString = request.inflow() != null ? String.join(",", request.inflow()) : null;
        
        Member member = memberRepository.save(
                Member.builder()
                        .email(request.email())
                        .nickname(request.name())
                        .school(request.school())
                        .major(request.major())
                        .inflow(inflowString)
                        .build());

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

    @Transactional
    public void kakaoQuit(Long memberId) {
        Member member = memberValidator.validateAndGetById(memberId);
        kakaoUnlinkClient.unlink(member.getKakaoAccessToken());
        member.delete();
    }

    @Transactional
    public void refreshToken(String refresh, HttpServletResponse response) {
        Long memberId = jwtTokenProvider.getMemberIdFromToken(refresh);
        Member member = memberValidator.validateAndGetById(memberId);
        if (!jwtTokenProvider.validateToken(refresh)) {
            throw new InvalidRefreshTokenException();
        }

        // 토큰 재발급
        String accessToken = jwtTokenProvider.createAccessToken(memberId, "member");
        String refreshToken = jwtTokenProvider.createRefreshToken(memberId);

        // 리프레시 토큰 저장
        member.updateRefreshToken(refreshToken);
        memberRepository.save(member);
        // 응답 헤더 설정
        response.setHeader("Authorization", "Bearer " + accessToken);

        Cookie refreshTokenCookie = new Cookie("refresh_token", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(refreshTokenCookie);
    }

}

