package com.example.forever.domain.member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberId {
    
    private Long value;
    
    public MemberId(Long value) {
        validateMemberId(value);
        this.value = value;
    }
    
    private void validateMemberId(Long memberId) {
        if (memberId == null || memberId <= 0) {
            throw new IllegalArgumentException("회원 ID는 양수여야 합니다.");
        }
    }

    public static MemberId of(Long value) {
        return new MemberId(value);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        MemberId memberId = (MemberId) obj;
        return value.equals(memberId.value);
    }
    
    @Override
    public int hashCode() {
        return value.hashCode();
    }
    
    @Override
    public String toString() {
        return value.toString();
    }
}
