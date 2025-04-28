package com.example.forever.exception.auth;

import com.example.forever.common.errorcode.AuthErrorCode;
import com.example.forever.common.exception.AuthException;

public class DeletedMemberException extends AuthException {
    public DeletedMemberException() {
        super(AuthErrorCode.DELETE_MEMBER);
    }
}