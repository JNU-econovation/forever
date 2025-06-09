package com.example.forever.exception.auth;

import com.example.forever.common.errorcode.AuthErrorCode;
import com.example.forever.common.exception.AuthException;

public class MemberNotFoundException extends AuthException {
    public MemberNotFoundException(Long memberId) {
        super(AuthErrorCode.MEMBER_NOT_FOUND);
    }
    
    public MemberNotFoundException(String message) {
        super(AuthErrorCode.MEMBER_NOT_FOUND);
    }
}
