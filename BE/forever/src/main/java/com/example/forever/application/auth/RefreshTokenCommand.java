package com.example.forever.application.auth;

public record RefreshTokenCommand(String refreshTokenValue) {
    
    public static RefreshTokenCommand of(String refreshTokenValue) {
        return new RefreshTokenCommand(refreshTokenValue);
    }
    
    public boolean hasRefreshToken() {
        return refreshTokenValue != null && !refreshTokenValue.trim().isEmpty();
    }
}
