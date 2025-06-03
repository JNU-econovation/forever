package com.example.forever.domain.member;

import com.example.forever.exception.auth.AlreadyExistsEmailException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailDuplicationChecker {
    
    private final MemberDomainRepository memberRepository;
    
    public void checkEmailNotDuplicated(Email email) {
        if (memberRepository.existsByEmail(email)) {
            throw new AlreadyExistsEmailException();
        }
    }
}
