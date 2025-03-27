package com.example.forever.common.validator;

import com.example.forever.domain.Member;
import com.example.forever.domain.Question;
import com.example.forever.exception.auth.UnauthorizedDocumentAccessException;

public class QuestionAuthorizationValidator {
    public static void validateMemberAccessToQuestion(Question question, Member member) {
        if (!question.getDocument().getMember().getId().equals(member.getId())) {
            throw new UnauthorizedDocumentAccessException(member.getId(), question.getDocument().getId());
        }
    }
}

