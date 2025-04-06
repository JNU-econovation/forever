package com.example.forever.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;

@Entity
@Table(name = "member_tb", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(length = 512)
    private String major;

    @Column(length = 512)
    private String refreshToken;

    @Builder.Default
    @Column
    private int availableTokens = 10;

    @Builder.Default
    @Column(name = "is_agreed_policy", nullable = false)
    private Boolean isAgreedPolicy = true;

    @Builder.Default
    @Column(name = "effective_date_policy", nullable = false)
    private LocalDate effectiveDatePolicy = LocalDate.of(2025, 4, 3);

    @Builder.Default
    @Column(name = "is_agreed_terms", nullable = false)
    private Boolean isAgreedTerms = true;

    @Builder.Default
    @Column(name = "effective_date_terms", nullable = false)
    private LocalDate effectiveDateTerms = LocalDate.of(2025, 4, 3);


    public void updateRefreshToken(String token) {
        this.refreshToken = token;
    }

    public void agreePolicy() {
        this.isAgreedPolicy = true;
    }

    public void useToken() {
        this.availableTokens--;
    }

    public boolean isAvailableTokens() {
        return this.availableTokens > 0;
    }

    public void agreeTerms() {
        this.isAgreedTerms = true;
    }

}
