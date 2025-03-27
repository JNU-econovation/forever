package com.example.forever.exception.document;

import com.example.forever.common.errorcode.QuestionErrorCode;
import com.example.forever.common.exception.QuestionException;

public class AnswerNotFoundException extends QuestionException {
    public AnswerNotFoundException(Long id) {
        super(QuestionErrorCode.ANSWER_NOT_FOUND);
    }
}
