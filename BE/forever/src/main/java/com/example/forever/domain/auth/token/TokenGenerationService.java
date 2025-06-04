package com.example.forever.domain.auth.token;

import com.example.forever.domain.member.MemberId;
import com.example.forever.infrastructure.auth.JwtTokenManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenGenerationService {
    
    private final JwtTokenManager jwtTokenManager;
    
    /**
     * 회원 ID를 기반으로 새로운 토큰 쌍을 생성합니다.
     */
    public TokenPair generateNewTokenPair(MemberId memberId) {
        if (memberId == null) {
            throw new IllegalArgumentException("회원 ID는 필수입니다.");
        }
        
        log.info("새로운 토큰 쌍 생성 시작: memberId={}", memberId.getValue());
        
        TokenPair tokenPair = jwtTokenManager.generateNewTokenPair(memberId);
        
        log.info("새로운 토큰 쌍 생성 완료: memberId={}", memberId.getValue());
        return tokenPair;
    }
}
