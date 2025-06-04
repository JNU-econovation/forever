package com.example.forever.domain.auth.token;

public class TokenPair {
    
    private final AccessToken accessToken;
    private final RefreshToken refreshToken;
    
    public TokenPair(AccessToken accessToken, RefreshToken refreshToken) {
        if (accessToken == null) {
            throw new IllegalArgumentException("액세스 토큰은 필수입니다.");
        }
        if (refreshToken == null) {
            throw new IllegalArgumentException("리프레시 토큰은 필수입니다.");
        }
        
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
    
    public AccessToken getAccessToken() {
        return accessToken;
    }
    
    public RefreshToken getRefreshToken() {
        return refreshToken;
    }
    
    public String getAccessTokenValue() {
        return accessToken.getValue();
    }
    
    public String getRefreshTokenValue() {
        return refreshToken.getValue();
    }
    
    @Override
    public String toString() {
        return String.format("TokenPair{accessToken='%s...', refreshToken='%s...'}",
                accessToken.getValue().substring(0, Math.min(10, accessToken.getValue().length())),
                refreshToken.getValue().substring(0, Math.min(10, refreshToken.getValue().length())));
    }
}
