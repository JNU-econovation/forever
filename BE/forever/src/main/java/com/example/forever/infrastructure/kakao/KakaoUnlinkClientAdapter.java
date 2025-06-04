package com.example.forever.infrastructure.kakao;

import com.example.forever.common.feign.kakao.client.KakaoUnlinkClient;
import com.example.forever.domain.auth.KakaoAccessToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KakaoUnlinkClientAdapter {
    
    private final KakaoUnlinkClient kakaoUnlinkClient;
    
    public void unlinkKakaoAccount(KakaoAccessToken accessToken) {
        try {
            kakaoUnlinkClient.unlink(accessToken.getBearerToken());
            log.info("카카오 계정 연결 해제 성공");
        } catch (Exception e) {
            log.error("카카오 계정 연결 해제 실패: {}", e.getMessage());
        }
    }
}
