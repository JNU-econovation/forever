package com.example.forever.common.validator;
import com.example.forever.domain.Document;
import com.example.forever.domain.Member;
import com.example.forever.exception.auth.MemberNotFoundException;
import com.example.forever.exception.auth.UnauthorizedDocumentAccessException;
import com.example.forever.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberValidator {

    private final MemberRepository memberRepository;

    public Member validateAndGetById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
    }

    public void validateExistence(Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new MemberNotFoundException(memberId);
        }
    }

}


