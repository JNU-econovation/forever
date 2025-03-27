package com.example.forever.exception.document;

import com.example.forever.common.errorcode.QuestionErrorCode;
import com.example.forever.common.exception.QuestionException;

public class QuestionNotFoundException extends QuestionException {
    public QuestionNotFoundException(Long id) {
        super(QuestionErrorCode.QUESTION_NOT_FOUND);
    }
}
