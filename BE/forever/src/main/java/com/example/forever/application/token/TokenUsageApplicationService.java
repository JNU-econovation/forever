package com.example.forever.application.token;

import com.example.forever.domain.Member;
import com.example.forever.domain.member.MemberDomainRepository;
import com.example.forever.domain.member.MemberId;
import com.example.forever.domain.member.TokenRefreshService;
import com.example.forever.dto.member.TokenUsageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TokenUsageApplicationService {
    
    private final MemberDomainRepository memberRepository;
    private final TokenRefreshService tokenRefreshService;
    
    /**
     * AI 서비스 사용 시 토큰을 차감합니다.
     * 자동으로 날짜를 체크하여 필요시 토큰을 충전합니다.
     */
    public void useTokenForAiService(Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        tokenRefreshService.useTokenWithAutoRefresh(member);

        memberRepository.save(member);
        
        log.info("AI 서비스 토큰 사용 완료: memberId={}", memberId);
    }
    
    /**
     * 회원의 토큰 정보를 조회합니다.
     */
    public TokenInfo getTokenInfo(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        
        // 필요시 토큰 자동 충전
        tokenRefreshService.refreshTokensIfNeeded(member);
        memberRepository.save(member);
        
        return new TokenInfo(
                member.getAvailableTokens(),
                member.getTotalUsageCount(),
                member.getLastTokenRefreshDate()
        );
    }
    
    /**
     * 남은 토큰 사용량 조회
     */
    @Transactional
    public TokenUsageResponse getRemainingUsage(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new com.example.forever.exception.auth.MemberNotFoundException("회원을 찾을 수 없습니다."));
        
        // 필요 시 토큰 자동 갱신
        member.autoRefreshTokensIfNeeded();

        // 변경사항이 있다면 저장
        if (member.shouldRefreshTokens()) {
            memberRepository.save(member);
        }
        
        return new TokenUsageResponse(member.getAvailableTokens());
    }

    /**
     * 수동으로 토큰을 충전합니다
     */
    public void manualRefreshTokens(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        member.refreshDailyTokens();
        memberRepository.save(member);

        log.info("수동 토큰 충전 완료: memberId={}", memberId);
    }
}
