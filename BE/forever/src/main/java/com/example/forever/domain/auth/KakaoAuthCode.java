package com.example.forever.domain.auth;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KakaoAuthCode {
    
    private String value;
    
    public KakaoAuthCode(String value) {
        validateAuthCode(value);
        this.value = value;
    }
    
    private void validateAuthCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("카카오 인가 코드는 필수입니다.");
        }
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        KakaoAuthCode that = (KakaoAuthCode) obj;
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
