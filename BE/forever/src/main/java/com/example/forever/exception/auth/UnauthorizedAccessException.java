package com.example.forever.exception.auth;

import com.example.forever.common.errorcode.AuthErrorCode;
import com.example.forever.common.exception.AuthException;

public class UnauthorizedAccessException extends AuthException {
    public UnauthorizedAccessException() {
        super(AuthErrorCode.UNAUTHORIZED_ACCESS);
    }
}