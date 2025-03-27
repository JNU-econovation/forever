package com.example.forever.common.feign.kakao.client;

import com.example.forever.common.feign.FeignClientConfig;
import com.example.forever.common.feign.kakao.dto.KakaoAccessTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "kakaoTokenClient", url = "https://kauth.kakao.com",
        configuration = FeignClientConfig.class)
public interface KakaoTokenClient {
    @PostMapping(value = "/oauth/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    KakaoAccessTokenResponse getAccessToken(
            @RequestParam("grant_type") String grantType,
            @RequestParam("client_id") String clientId,
            @RequestParam("code") String code
    );
}

