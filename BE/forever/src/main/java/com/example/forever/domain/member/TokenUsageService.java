package com.example.forever.domain.member;

import com.example.forever.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 토큰 사용량 관련 도메인 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional
public class TokenUsageService {
    
    private final MemberDomainRepository memberDomainRepository;
    
    /**
     * 회원의 남은 토큰 사용량 조회
     */
    public int getRemainingUsage(MemberId memberId) {
        Member member = memberDomainRepository.findById(memberId.getValue())
                .orElseThrow(() -> new com.example.forever.exception.auth.MemberNotFoundException("회원을 찾을 수 없습니다."));
        
        // 필요 시 토큰 자동 갱신
        member.autoRefreshTokensIfNeeded();
        
        // 변경사항이 있다면 저장
        if (member.shouldRefreshTokens()) {
            memberDomainRepository.save(member);
        }
        
        return member.getAvailableTokens();
    }
}
