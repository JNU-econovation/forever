package com.example.forever.domain;

import java.time.LocalDateTime;
import lombok.Builder;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "verification_code_tb")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class VerificationCode extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String email;

    @Column(nullable = false, length = 10)
    private String code;

    @Column(name = "expires_time", nullable = false)
    private LocalDateTime expiresTime;

}
