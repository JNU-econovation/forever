package com.example.forever.domain.auth;

import com.example.forever.infrastructure.kakao.KakaoUnlinkClientAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoUnlinkService {
    
    private final KakaoUnlinkClientAdapter kakaoUnlinkClient;
    
    public void unlinkKakaoAccount(KakaoAccessToken accessToken) {
        if (accessToken == null) {
            throw new IllegalArgumentException("카카오 액세스 토큰이 필요합니다.");
        }
        
        kakaoUnlinkClient.unlinkKakaoAccount(accessToken);
    }
}
