package com.example.forever.dto.member;

public record SignUpRequest(
        String name,
        String major,
        String school,
        String verificationCode
) {
}
