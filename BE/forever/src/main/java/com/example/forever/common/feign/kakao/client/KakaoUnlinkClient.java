package com.example.forever.common.feign.kakao.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "kakaoApi", url = "https://kapi.kakao.com")
public interface KakaoUnlinkClient {

    @PostMapping("/v1/user/unlink")
    void unlink(@RequestHeader("Authorization") String authorizationHeader);
}
