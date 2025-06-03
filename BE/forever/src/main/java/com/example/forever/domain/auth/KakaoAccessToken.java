package com.example.forever.domain.auth;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KakaoAccessToken {
    
    private String value;
    
    public KakaoAccessToken(String value) {
        validateAccessToken(value);
        this.value = value;
    }
    
    private void validateAccessToken(String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("카카오 액세스 토큰은 필수입니다.");
        }
    }
    
    public String getBearerToken() {
        return "Bearer " + value;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        KakaoAccessToken that = (KakaoAccessToken) obj;
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
