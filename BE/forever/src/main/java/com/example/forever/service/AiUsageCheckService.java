package com.example.forever.service;

import com.example.forever.common.annotation.MemberInfo;
import com.example.forever.common.validator.MemberValidator;
import com.example.forever.domain.Member;
import com.example.forever.domain.member.TokenRefreshService;
import com.example.forever.dto.AiTokenUsageResponse;
import com.example.forever.exception.token.InsufficientTokenException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AiUsageCheckService {

    private final MemberValidator memberValidator;
    private final TokenRefreshService tokenRefreshService;

    public AiTokenUsageResponse checkTokenUsage(MemberInfo memberInfo) {
        Member member = memberValidator.validateAndGetById(memberInfo.getMemberId());
        tokenRefreshService.refreshTokensIfNeeded(member);
        
        log.info("토큰 사용량 체크: memberId={}, availableTokens={}, totalUsage={}", 
                member.getId(), member.getAvailableTokens(), member.getTotalUsageCount());
        
        return new AiTokenUsageResponse(member.isAvailableTokens());
    }

    public void checkCompleteUpload(MemberInfo memberInfo) {
        Member member = memberValidator.validateAndGetById(memberInfo.getMemberId());
        
        try {
            // 토큰 사용
            tokenRefreshService.useTokenWithAutoRefresh(member);
            
            log.info("AI 서비스 사용 완료: memberId={}, 잔여토큰={}", 
                    member.getId(), member.getAvailableTokens());
                    
        } catch (InsufficientTokenException e) {
            log.warn("토큰 부족으로 AI 서비스 사용 실패: memberId={}", member.getId());
            throw new RuntimeException("사용 가능한 토큰이 없습니다. 내일 다시 시도해주세요.", e);
        }
    }
}
