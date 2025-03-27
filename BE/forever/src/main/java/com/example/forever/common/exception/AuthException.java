package com.example.forever.common.exception;

import com.example.forever.common.errorcode.ErrorCode;

public abstract class AuthException extends BaseException {

    protected AuthException(ErrorCode errorCode) {
        super(errorCode);
    }
}
