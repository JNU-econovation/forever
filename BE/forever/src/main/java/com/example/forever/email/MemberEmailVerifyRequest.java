package com.example.forever.email;

public record MemberEmailVerifyRequest(
        String email,
        String verificationCode
) {
}
