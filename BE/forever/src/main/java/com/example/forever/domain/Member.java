package com.example.forever.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.*;
import com.example.forever.domain.member.MarketingAgreement;
import com.example.forever.exception.token.InsufficientTokenException;

@Entity
@Table(name = "member_tb", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(length = 512)
    private String major;

    @Column(length = 512)
    private String school;

    @Column(length = 1024)
    private String inflow;

    @Column(length = 512)
    private String refreshToken;

    @Column(length = 512)
    private String kakaoAccessToken;

    @Builder.Default
    @Column
    private int availableTokens = 3;

    @Builder.Default
    @Column(name = "total_usage_count")
    private int totalUsageCount = 0;

    @Builder.Default
    @Column(name = "last_token_refresh_date")
    private LocalDate lastTokenRefreshDate = LocalDate.now();

    @Builder.Default
    private boolean isDeleted = false;

    private LocalDateTime deletedAt;

    @Builder.Default
    @Column(name = "is_agreed_policy", nullable = false)
    private Boolean isAgreedPolicy = true;

    @Builder.Default
    @Column(name = "effective_date_policy", nullable = false)
    private LocalDate effectiveDatePolicy = LocalDate.of(2025, 6, 17);

    @Builder.Default
    @Column(name = "is_agreed_terms", nullable = false)
    private Boolean isAgreedTerms = true;

    @Builder.Default
    @Column(name = "effective_date_terms", nullable = false)
    private LocalDate effectiveDateTerms = LocalDate.of(2025, 6, 17);

    @Embedded
    @Builder.Default
    private MarketingAgreement marketingAgreement = MarketingAgreement.of(false);


    public void updateRefreshToken(String token) {
        this.refreshToken = token;
    }

    public void updateKakaoAccessToken(String token) {
        this.kakaoAccessToken = token;
    }

    public void agreePolicy() {
        this.isAgreedPolicy = true;
    }

    public void useToken() {
        if (!isAvailableTokens()) {
            throw new InsufficientTokenException("사용 가능한 토큰이 없습니다.");
        }
        this.availableTokens--;
        this.totalUsageCount++;
    }

    public boolean isAvailableTokens() {
        return this.availableTokens > 0;
    }

    public void refreshDailyTokens() {
        this.availableTokens = 3;
        this.lastTokenRefreshDate = LocalDate.now();
    }

    public boolean shouldRefreshTokens() {
        return !this.lastTokenRefreshDate.equals(LocalDate.now());
    }

    public void autoRefreshTokensIfNeeded() {
        if (shouldRefreshTokens()) {
            refreshDailyTokens();
        }
    }

    public void agreeTerms() {
        this.isAgreedTerms = true;
    }

    public void delete() {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }

    /**
     * 마케팅 동의 상태 변경 DDD의 Aggregate Root가 자신의 상태 변경을 관리
     */
    public void updateMarketingAgreement(boolean isAgreed) {
        this.marketingAgreement = this.marketingAgreement.updateAgreement(isAgreed);
    }

    /**
     * 마케팅 동의 정보 설정 (생성 시에만 사용)
     */
    public void setMarketingAgreement(boolean isAgreed) {
        this.marketingAgreement = MarketingAgreement.of(isAgreed);
    }

}
