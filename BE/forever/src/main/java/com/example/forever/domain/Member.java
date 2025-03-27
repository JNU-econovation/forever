package com.example.forever.domain;

import jakarta.persistence.*;
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

    @Column(name = "agreed_to_privacy", nullable = false)
    private boolean agreedToPrivacy;

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateRefreshToken(String token) {
        this.refreshToken = token;
    }


}
