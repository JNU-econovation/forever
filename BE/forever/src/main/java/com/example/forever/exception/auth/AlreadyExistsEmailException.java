package com.example.forever.exception.auth;

import com.example.forever.common.errorcode.AuthErrorCode;
import com.example.forever.common.exception.AuthException;

public class AlreadyExistsEmailException extends AuthException {
    public AlreadyExistsEmailException() {
        super(AuthErrorCode.ALREADY_EXISTS_EMAIL);
    }
}