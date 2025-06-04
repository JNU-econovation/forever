package com.example.forever.domain.auth.token;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccessToken {
    
    private String value;
    
    public AccessToken(String value) {
        validateAccessToken(value);
        this.value = value;
    }
    
    private void validateAccessToken(String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("액세스 토큰은 필수입니다.");
        }
        
        // JWT 형식 기본 검증
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            throw new IllegalArgumentException("유효하지 않은 토큰 형식입니다.");
        }
    }
    
    public String getBearerToken() {
        return "Bearer " + value;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AccessToken that = (AccessToken) obj;
        return value.equals(that.value);
    }
    
    @Override
    public int hashCode() {
        return value.hashCode();
    }
    
    @Override
    public String toString() {
        return value;
    }
}
