package com.example.forever.exception.auth;

import com.example.forever.common.errorcode.AuthErrorCode;
import com.example.forever.common.exception.AuthException;

public class UnauthorizedDocumentAccessException extends AuthException {
    public UnauthorizedDocumentAccessException(Long memberId, Long documentId) {
        super(AuthErrorCode.UNAUTHORIZED_DOCUMENT_ACCESS);
    }
}