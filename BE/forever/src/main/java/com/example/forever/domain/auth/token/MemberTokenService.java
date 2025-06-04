package com.example.forever.domain.auth.token;

import com.example.forever.domain.Member;
import com.example.forever.domain.member.MemberId;
import com.example.forever.domain.member.MemberDomainRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberTokenService {
    
    private final MemberDomainRepository memberRepository;
    
    /**
     * 회원 ID로 회원을 조회하고 검증합니다.
     */
    public Member findAndValidateMember(MemberId memberId) {
        Member member = memberRepository.findById(memberId.getValue())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        
        if (member.isDeleted()) {
            throw new IllegalStateException("탈퇴한 회원입니다.");
        }
        
        return member;
    }
    
    /**
     * 회원의 리프레시 토큰을 업데이트합니다.
     */
    public void updateMemberRefreshToken(Member member, RefreshToken newRefreshToken) {
        member.updateRefreshToken(newRefreshToken.getValue());
        memberRepository.save(member);
        
        log.info("회원 리프레시 토큰 업데이트 완료: memberId={}", member.getId());
    }
}
