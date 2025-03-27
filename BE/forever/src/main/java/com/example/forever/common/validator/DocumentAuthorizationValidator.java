package com.example.forever.common.validator;

import com.example.forever.domain.Document;
import com.example.forever.domain.Member;
import com.example.forever.exception.auth.UnauthorizedDocumentAccessException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DocumentAuthorizationValidator {

    public static void validateAuthor(Document document, Member member) {
        if (!document.getMember().getId().equals(member.getId())) {
            throw new UnauthorizedDocumentAccessException(member.getId(), document.getId());
        }
    }
}
