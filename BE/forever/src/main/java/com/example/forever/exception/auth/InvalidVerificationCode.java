package com.example.forever.exception.auth;

import com.example.forever.common.errorcode.AuthErrorCode;
import com.example.forever.common.exception.AuthException;

public class InvalidVerificationCode extends AuthException {
    public InvalidVerificationCode() {
        super(AuthErrorCode.INVALID_VERIFICATION_CODE);
    }
}
