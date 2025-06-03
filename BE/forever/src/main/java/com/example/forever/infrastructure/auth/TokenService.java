package com.example.forever.infrastructure.auth;

import com.example.forever.common.auth.JwtTokenProvider;
import com.example.forever.domain.Member;
import com.example.forever.domain.member.MemberDomainRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {
    
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberDomainRepository memberRepository;
    private final AuthenticationResponseBuilder responseBuilder;
    
    public void generateAndSetTokens(Member member, HttpServletResponse response) {
        // 토큰 생성
        String accessToken = jwtTokenProvider.createAccessToken(member.getId(), "member");
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getId());
        
        // 멤버에 리프레시 토큰 저장
        member.updateRefreshToken(refreshToken);
        memberRepository.save(member);
        
        // HTTP 응답 설정
        responseBuilder.setAuthenticationResponse(response, accessToken, refreshToken);
    }
}
