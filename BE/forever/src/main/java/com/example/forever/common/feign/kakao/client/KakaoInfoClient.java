package com.example.forever.common.feign.kakao.client;

import com.example.forever.common.feign.FeignClientConfig;
import com.example.forever.common.feign.kakao.dto.KakaoMemberInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "kakaoInfoClient", url = "https://kapi.kakao.com", configuration = FeignClientConfig.class)
public interface KakaoInfoClient {

    @GetMapping("/v2/user/me")
    KakaoMemberInfoResponse getUserInfo(@RequestHeader("Authorization") String authorization);
}