package com.example.forever.infrastructure.kakao;

import com.example.forever.common.feign.kakao.client.KakaoInfoClient;
import com.example.forever.common.feign.kakao.client.KakaoTokenClient;
import com.example.forever.common.feign.kakao.dto.KakaoAccessTokenResponse;
import com.example.forever.common.feign.kakao.dto.KakaoMemberInfoResponse;
import com.example.forever.domain.auth.KakaoAccessToken;
import com.example.forever.domain.auth.KakaoAuthCode;
import com.example.forever.domain.member.Email;
import com.example.forever.exception.auth.InvalidKakaoCodeException;
import feign.FeignException.FeignClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KakaoOAuthClient {
    
    private final KakaoTokenClient kakaoTokenClient;
    private final KakaoInfoClient kakaoInfoClient;
    
    @Value("${kakao.client.id}")
    private String kakaoClientId;
    
    public KakaoAccessToken getAccessToken(KakaoAuthCode authCode) {
        try {
            KakaoAccessTokenResponse response = kakaoTokenClient.getAccessToken(
                    "authorization_code", 
                    kakaoClientId, 
                    authCode.getValue()
            );
            return new KakaoAccessToken(response.accessToken());
        } catch (FeignClientException e) {
            if (e.status() == 400 || e.status() == 401) {
                throw new InvalidKakaoCodeException();
            }
            throw e;
        }
    }
    
    public Email getUserEmail(KakaoAccessToken accessToken) {
        try {
            KakaoMemberInfoResponse response = kakaoInfoClient.getUserInfo(accessToken.getBearerToken());
            String email = response.kakaoAccount().email();
            return new Email(email);
        } catch (FeignClientException e) {
            if (e.status() == 400 || e.status() == 401) {
                throw new InvalidKakaoCodeException();
            }
            throw e;
        }
    }
}
