package com.example.forever.domain.auth.token;

import com.example.forever.domain.member.MemberId;
import com.example.forever.infrastructure.auth.JwtTokenManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenValidationService {
    
    private final JwtTokenManager jwtTokenManager;
    
    /**
     * 리프레시 토큰의 유효성을 검증합니다.
     */
    public boolean isValidRefreshToken(RefreshToken refreshToken) {
        if (refreshToken == null || refreshToken.isEmpty()) {
            log.warn("리프레시 토큰이 비어있습니다.");
            return false;
        }
        
        return jwtTokenManager.isValidRefreshToken(refreshToken);
    }
    
    /**
     * 리프레시 토큰에서 회원 ID를 추출합니다.
     */
    public MemberId extractMemberIdFromToken(RefreshToken refreshToken) {
        if (!isValidRefreshToken(refreshToken)) {
            throw new IllegalArgumentException("유효하지 않은 리프레시 토큰입니다.");
        }
        
        return jwtTokenManager.extractMemberIdFromRefreshToken(refreshToken);
    }
    
    /**
     * 리프레시 토큰이 null이거나 비어있는지 확인합니다.
     */
    public void validateRefreshTokenPresence(RefreshToken refreshToken) {
        if (refreshToken == null || refreshToken.isEmpty()) {
            throw new IllegalArgumentException("리프레시 토큰이 필요합니다.");
        }
    }
}
