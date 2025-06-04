package com.example.forever.infrastructure.auth;

import com.example.forever.common.auth.JwtTokenProvider;
import com.example.forever.domain.auth.token.AccessToken;
import com.example.forever.domain.auth.token.RefreshToken;
import com.example.forever.domain.auth.token.TokenPair;
import com.example.forever.domain.member.MemberId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenManager {
    
    private final JwtTokenProvider jwtTokenProvider;
    
    /**
     * 리프레시 토큰의 유효성을 검증합니다.
     */
    public boolean isValidRefreshToken(RefreshToken refreshToken) {
        try {
            return jwtTokenProvider.validateToken(refreshToken.getValue());
        } catch (Exception e) {
            log.warn("리프레시 토큰 검증 실패: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * 리프레시 토큰에서 회원 ID를 추출합니다.
     */
    public MemberId extractMemberIdFromRefreshToken(RefreshToken refreshToken) {
        try {
            Long memberId = jwtTokenProvider.getMemberIdFromToken(refreshToken.getValue());
            return new MemberId(memberId);
        } catch (Exception e) {
            log.error("리프레시 토큰에서 회원 ID 추출 실패: {}", e.getMessage());
            throw new IllegalArgumentException("유효하지 않은 리프레시 토큰입니다.", e);
        }
    }
    
    /**
     * 새로운 토큰 쌍을 생성합니다.
     */
    public TokenPair generateNewTokenPair(MemberId memberId) {
        try {
            String accessTokenValue = jwtTokenProvider.createAccessToken(memberId.getValue(), "member");
            String refreshTokenValue = jwtTokenProvider.createRefreshToken(memberId.getValue());
            
            AccessToken accessToken = new AccessToken(accessTokenValue);
            RefreshToken refreshToken = new RefreshToken(refreshTokenValue);
            
            log.info("새로운 토큰 쌍 생성 완료: memberId={}", memberId.getValue());
            return new TokenPair(accessToken, refreshToken);
            
        } catch (Exception e) {
            log.error("토큰 생성 실패: memberId={}, error={}", memberId.getValue(), e.getMessage());
            throw new RuntimeException("토큰 생성 중 오류가 발생했습니다.", e);
        }
    }
}
