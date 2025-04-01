package com.example.forever.exception.auth;

import com.example.forever.common.errorcode.AuthErrorCode;
import com.example.forever.common.exception.AuthException;

public class NoAvailableUsageException extends AuthException {
    public NoAvailableUsageException() {
        super(AuthErrorCode.NO_AVAILABLE_USAGE);
    }
}
