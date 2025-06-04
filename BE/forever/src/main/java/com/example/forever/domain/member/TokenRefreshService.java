package com.example.forever.domain.member;

import com.example.forever.domain.Member;
import com.example.forever.exception.token.InsufficientTokenException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenRefreshService {
    
    /**
     * 필요시 토큰을 자동 충전합니다.
     */
    public void refreshTokensIfNeeded(Member member) {
        if (member.shouldRefreshTokens()) {
            member.refreshDailyTokens();
            log.info("회원 토큰 자동 충전: memberId={}, date={}", 
                    member.getId(), member.getLastTokenRefreshDate());
        }
    }
    
    /**
     * 토큰 사용 가능 여부를 확인하고, 필요시 자동 충전합니다.
     */
    public void ensureTokensAvailable(Member member) {
        refreshTokensIfNeeded(member);
        
        if (!member.isAvailableTokens()) {
            throw new InsufficientTokenException(
                    String.format("일일 토큰을 모두 사용했습니다. 사용 가능 토큰: %d개", 
                            member.getAvailableTokens()));
        }
    }
    
    /**
     * 토큰 사용 처리 (자동 충전 + 사용)
     */
    public void useTokenWithAutoRefresh(Member member) {
        ensureTokensAvailable(member);
        member.useToken();
        
        log.info("토큰 사용: memberId={}, 잔여토큰={}, 총사용량={}", 
                member.getId(), member.getAvailableTokens(), member.getTotalUsageCount());
    }
}
