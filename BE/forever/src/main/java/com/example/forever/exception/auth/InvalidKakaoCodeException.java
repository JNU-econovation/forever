package com.example.forever.exception.auth;


import com.example.forever.common.errorcode.AuthErrorCode;
import com.example.forever.common.exception.AuthException;

public class InvalidKakaoCodeException extends AuthException {
    public InvalidKakaoCodeException() {
        super(AuthErrorCode.INVALID_KAKAO_CODE);
    }
}
