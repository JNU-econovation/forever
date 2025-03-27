package com.example.forever.dto.member;

public record LoginTokenResponse(
        String accessToken,
        String refreshToken
) {}