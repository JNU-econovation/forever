package com.example.forever.infrastructure.scheduler;

import com.example.forever.domain.Member;
import com.example.forever.domain.member.MemberDomainRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DailyTokenRefreshScheduler {
    
    private final MemberDomainRepository memberRepository;
    
    /**
     * 매일 자정에 모든 활성 회원의 토큰을 3개로 충전합니다.
     */
    @Scheduled(cron = "0 0 0 * * *")  // 매일 자정 실행
    @Transactional
    public void refreshAllMembersTokens() {
        try {
            List<Member> activeMembers = memberRepository.findAllActiveMembers();
            
            int refreshedCount = 0;
            for (Member member : activeMembers) {
                if (member.shouldRefreshTokens()) {
                    member.refreshDailyTokens();
                    memberRepository.save(member);
                    refreshedCount++;
                }
            }
            
            log.info("일일 토큰 자동 충전 완료: 전체 {}명 중 {}명 충전됨", 
                    activeMembers.size(), refreshedCount);
                    
        } catch (Exception e) {
            log.error("일일 토큰 충전 중 오류 발생", e);
        }
    }
    
    /**
     * 테스트용: 1분마다 실행 (개발/테스트 환경에서만 사용)
     * 실제 운영에서는 제거하거나 주석 처리
     */
    // @Scheduled(fixedRate = 60000)  // 1분마다 실행 (테스트용)
    public void testTokenRefresh() {
        log.debug("토큰 충전 스케줄러 동작 확인 - 현재 시각: {}", 
                java.time.LocalDateTime.now());
    }
}
