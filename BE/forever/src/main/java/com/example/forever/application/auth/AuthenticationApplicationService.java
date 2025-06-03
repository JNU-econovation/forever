package com.example.forever.application.auth;

import com.example.forever.domain.Member;
import com.example.forever.domain.auth.*;
import com.example.forever.domain.member.MemberDomainRepository;
import com.example.forever.exception.auth.DeletedMemberException;
import com.example.forever.infrastructure.auth.TokenService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationApplicationService {
    
    private final KakaoOAuthService kakaoOAuthService;
    private final MemberAuthenticationService memberAuthenticationService;
    private final MemberDomainRepository memberRepository;
    private final TokenService tokenService;
    
    public LoginResult login(LoginCommand command, HttpServletResponse response) {
        // 카카오 인가 코드 검증 및 값 객체 생성
        KakaoAuthCode authCode = new KakaoAuthCode(command.code());
        
        // 카카오 OAuth 인증
        KakaoOAuthService.KakaoAuthenticationInfo kakaoInfo = kakaoOAuthService.authenticate(authCode);
        
        // 회원 인증 상태 확인
        AuthenticationResult authResult = memberAuthenticationService.authenticateMember(
                kakaoInfo.getEmail(), 
                kakaoInfo.getAccessToken()
        );
        
        // 인증 결과에 따른 처리
        if (authResult.isDeletedMember()) {
            throw new DeletedMemberException();
        }
        
        if (authResult.isSignupRequired()) {
            return LoginResult.forSignupRequired(authResult.getEmail().getValue());
        }
        
        // 기존 회원 로그인 처리
        Member member = authResult.getMember();
        
        // 카카오 액세스 토큰 저장
        member.updateKakaoAccessToken(authResult.getKakaoAccessToken().getBearerToken());
        memberRepository.save(member);
        
        // 토큰 발급 및 응답 설정
        tokenService.generateAndSetTokens(member, response);
        
        return LoginResult.forExistingMember(
                member.getNickname(),
                member.getMajor(), 
                member.getSchool()
        );
    }
}
