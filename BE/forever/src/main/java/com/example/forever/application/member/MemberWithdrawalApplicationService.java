package com.example.forever.application.member;

import com.example.forever.domain.member.MemberId;
import com.example.forever.domain.member.MemberWithdrawalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberWithdrawalApplicationService {
    
    private final MemberWithdrawalService memberWithdrawalService;
    
    public WithdrawResult withdraw(WithdrawCommand command) {
        try {
            MemberId memberId = new MemberId(command.memberId());

            memberWithdrawalService.withdrawMember(memberId);
            
            return WithdrawResult.createSuccess();
            
        } catch (IllegalArgumentException | IllegalStateException e) {
            log.warn("회원탈퇴 실패: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("회원탈퇴 처리 중 예상치 못한 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("회원탈퇴 처리 중 오류가 발생했습니다.", e);
        }
    }
}
