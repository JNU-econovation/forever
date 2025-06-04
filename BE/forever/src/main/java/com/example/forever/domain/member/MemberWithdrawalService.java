package com.example.forever.domain.member;

import com.example.forever.domain.Member;
import com.example.forever.domain.auth.KakaoAccessToken;
import com.example.forever.domain.auth.KakaoUnlinkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberWithdrawalService {
    
    private final MemberDomainRepository memberRepository;
    private final KakaoUnlinkService kakaoUnlinkService;
    private final ApplicationEventPublisher eventPublisher;
    
    public void withdrawMember(MemberId memberId) {
        Member member = memberRepository.findById(memberId.getValue())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        
        // 이미 탈퇴한 회원인지 확인
        if (member.isDeleted()) {
            throw new IllegalStateException("이미 탈퇴한 회원입니다.");
        }
        
        // 카카오 계정 연결 해제
        if (member.getKakaoAccessToken() != null) {
            KakaoAccessToken accessToken = new KakaoAccessToken(
                    member.getKakaoAccessToken().replace("Bearer ", "")
            );
            kakaoUnlinkService.unlinkKakaoAccount(accessToken);
        }
        
        // 회원 소프트 삭제
        member.delete();
        memberRepository.save(member);
        
        // 도메인 이벤트 발행
        MemberWithdrawnEvent event = new MemberWithdrawnEvent(
                memberId,
                new Email(member.getEmail()),
                "사용자 요청"
        );
        eventPublisher.publishEvent(event);
        
        log.info("회원 탈퇴 완료: memberId={}", memberId.getValue());
    }
}
