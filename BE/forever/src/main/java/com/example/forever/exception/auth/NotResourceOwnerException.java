package com.example.forever.exception.auth;

import com.example.forever.common.errorcode.AuthErrorCode;
import com.example.forever.common.exception.AuthException;

public class NotResourceOwnerException extends AuthException {
    public NotResourceOwnerException() {
        super(AuthErrorCode.NOT_RESOURCE_OWNER);
    }
}
