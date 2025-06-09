package com.example.forever.domain.member;

import com.example.forever.domain.Member;
import com.example.forever.exception.auth.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 마케팅 동의 관련 도메인 서비스
 * DDD의 Domain Service 패턴을 적용하여 비즈니스 로직을 캡슐화
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MarketingAgreementService {
    
    private final MemberDomainRepository memberDomainRepository;
    
    /**
     * 회원의 마케팅 동의 정보 조회
     */
    public MarketingAgreement getMarketingAgreement(MemberId memberId) {
        Member member = memberDomainRepository.findById(memberId.getValue())
                .orElseThrow(() -> new MemberNotFoundException("회원을 찾을 수 없습니다."));
        return member.getMarketingAgreement();
    }
    
    /**
     * 마케팅 동의 상태 변경
     */
    @Transactional
    public MarketingAgreement updateMarketingAgreement(MemberId memberId, boolean isAgreed) {
        Member member = memberDomainRepository.findById(memberId.getValue())
                .orElseThrow(() -> new MemberNotFoundException("회원을 찾을 수 없습니다."));
        member.updateMarketingAgreement(isAgreed);
        
        Member savedMember = memberDomainRepository.save(member);
        return savedMember.getMarketingAgreement();
    }
}
