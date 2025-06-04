package com.example.forever.domain.auth.token;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {
    
    private String value;
    
    public RefreshToken(String value) {
        validateRefreshToken(value);
        this.value = value;
    }
    
    private void validateRefreshToken(String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("리프레시 토큰은 필수입니다.");
        }
        
        // JWT 형식 기본 검증 (3개 부분으로 구성되어야 함)
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            throw new IllegalArgumentException("유효하지 않은 토큰 형식입니다.");
        }
    }
    
    public boolean isEmpty() {
        return value == null || value.trim().isEmpty();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        RefreshToken that = (RefreshToken) obj;
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
