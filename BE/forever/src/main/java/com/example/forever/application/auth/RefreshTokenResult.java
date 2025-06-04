package com.example.forever.application.auth;

public record RefreshTokenResult(
        String accessToken,
        String refreshToken,
        boolean success,
        String message
) {
    
    public static RefreshTokenResult success(String accessToken, String refreshToken) {
        return new RefreshTokenResult(accessToken, refreshToken, true, "토큰 재발급 성공");
    }
    
    public static RefreshTokenResult failure(String message) {
        return new RefreshTokenResult(null, null, false, message);
    }
}
