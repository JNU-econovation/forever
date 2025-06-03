package com.example.forever.domain.member;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Major {
    
    private static final int MAX_LENGTH = 100;
    
    private String value;
    
    public Major(String value) {
        validateMajor(value);
        this.value = value;
    }
    
    private void validateMajor(String major) {
        if (major == null || major.trim().isEmpty()) {
            throw new IllegalArgumentException("전공은 필수입니다.");
        }
        
        String trimmed = major.trim();
        if (trimmed.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(
                String.format("전공은 %d자 이하여야 합니다.", MAX_LENGTH));
        }
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Major major = (Major) obj;
        return value.equals(major.value);
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
