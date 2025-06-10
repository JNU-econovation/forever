package com.example.forever.domain.member;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 마케팅 동의 정보를 나타내는 Value Object
 * DDD의 Value Object 패턴을 적용하여 마케팅 동의와 관련된 데이터를 캡슐화
 */
@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MarketingAgreement {
    
    @Column(name = "marketing_agreement")
    private boolean isAgreed;
    
    @Column(name = "marketing_agreement_date")
    private LocalDate agreementDate;
    
    /**
     * 마케팅 동의 생성 팩토리 메서드
     */
    public static MarketingAgreement of(boolean isAgreed) {
        return new MarketingAgreement(isAgreed, LocalDate.of(2025, 6, 17));
    }
    
    /**
     * 마케팅 동의 상태 변경
     * 불변성을 위해 새로운 객체를 반환
     */
    public MarketingAgreement updateAgreement(boolean newAgreement) {
        if (this.isAgreed == newAgreement) {
            return this;
        }
        return new MarketingAgreement(newAgreement, LocalDate.of(2025, 6, 17));
    }
    
    /**
     * 동의 여부 확인
     */
    public boolean isAgreed() {
        return Boolean.TRUE.equals(isAgreed);
    }
    
    /**
     * 동의 날짜 조회 (방어적 복사)
     */
    public LocalDate getAgreementDate() {
        return agreementDate;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MarketingAgreement that = (MarketingAgreement) o;
        return Objects.equals(isAgreed, that.isAgreed) && 
               Objects.equals(agreementDate, that.agreementDate);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(isAgreed, agreementDate);
    }
}
