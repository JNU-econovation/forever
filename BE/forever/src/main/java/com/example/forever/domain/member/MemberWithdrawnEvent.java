package com.example.forever.domain.member;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class MemberWithdrawnEvent {
    
    private final MemberId memberId;
    private final Email email;
    private final LocalDateTime withdrawnAt;
    private final String reason;
    
    public MemberWithdrawnEvent(MemberId memberId, Email email, String reason) {
        this.memberId = memberId;
        this.email = email;
        this.withdrawnAt = LocalDateTime.now();
        this.reason = reason;
    }
    
    @Override
    public String toString() {
        return String.format("MemberWithdrawnEvent{memberId=%s, email=%s, withdrawnAt=%s, reason='%s'}", 
                memberId, email, withdrawnAt, reason);
    }
}
