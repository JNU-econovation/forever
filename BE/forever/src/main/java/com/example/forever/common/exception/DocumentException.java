package com.example.forever.common.exception;

import com.example.forever.common.errorcode.ErrorCode;

public abstract class DocumentException extends BaseException {
    protected DocumentException(ErrorCode errorCode) {
        super(errorCode);
    }
}