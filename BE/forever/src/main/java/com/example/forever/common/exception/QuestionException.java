package com.example.forever.common.exception;

import com.example.forever.common.errorcode.ErrorCode;

public abstract class QuestionException extends BaseException {
    protected QuestionException(ErrorCode errorCode) {
        super(errorCode);
    }
}