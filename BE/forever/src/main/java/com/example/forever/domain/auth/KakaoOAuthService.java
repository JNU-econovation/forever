package com.example.forever.domain.auth;

import com.example.forever.domain.member.Email;
import com.example.forever.infrastructure.kakao.KakaoOAuthClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoOAuthService {
    
    private final KakaoOAuthClient kakaoOAuthClient;
    
    public KakaoAuthenticationInfo authenticate(KakaoAuthCode authCode) {
        // 카카오 액세스 토큰
        KakaoAccessToken accessToken = kakaoOAuthClient.getAccessToken(authCode);
        
        // 사용자 이메일 정보
        Email email = kakaoOAuthClient.getUserEmail(accessToken);
        
        return new KakaoAuthenticationInfo(email, accessToken);
    }
    
    public static class KakaoAuthenticationInfo {
        private final Email email;
        private final KakaoAccessToken accessToken;
        
        public KakaoAuthenticationInfo(Email email, KakaoAccessToken accessToken) {
            this.email = email;
            this.accessToken = accessToken;
        }
        
        public Email getEmail() {
            return email;
        }
        
        public KakaoAccessToken getAccessToken() {
            return accessToken;
        }
    }
}
