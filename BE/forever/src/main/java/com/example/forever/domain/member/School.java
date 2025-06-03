package com.example.forever.domain.member;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class School {
    
    private static final int MAX_LENGTH = 100;
    
    private String value;
    
    public School(String value) {
        validateSchool(value);
        this.value = value;
    }
    
    private void validateSchool(String school) {
        if (school == null || school.trim().isEmpty()) {
            throw new IllegalArgumentException("학교명은 필수입니다.");
        }
        
        String trimmed = school.trim();
        if (trimmed.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(
                String.format("학교명은 %d자 이하여야 합니다.", MAX_LENGTH));
        }
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        School school = (School) obj;
        return value.equals(school.value);
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
