package com.example.forever.exception.auth;

import com.example.forever.common.errorcode.AuthErrorCode;
import com.example.forever.common.exception.AuthException;

public class InvalidRefreshTokenException extends AuthException {
  public InvalidRefreshTokenException() {
    super(AuthErrorCode.INVALID_REFRESH_TOKEN);
  }
}