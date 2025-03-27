package com.example.forever.exception.auth;

import com.example.forever.common.errorcode.AuthErrorCode;
import com.example.forever.common.exception.AuthException;

public class OnboardingRequiredException extends AuthException {
    public OnboardingRequiredException() {
        super(AuthErrorCode.ONBOARDING_REQUIRED);
    }
}
