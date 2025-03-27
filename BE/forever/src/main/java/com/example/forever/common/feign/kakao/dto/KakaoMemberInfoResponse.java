package com.example.forever.common.feign.kakao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KakaoMemberInfoResponse(
        Long id,

        @JsonProperty("kakao_account")
        KakaoAccount kakaoAccount
) {
    public record KakaoAccount(
            Profile profile,

            @JsonProperty("email")
            String email
    ) {
        public record Profile(
                @JsonProperty("nickname")
                String nickname
        ) {}
    }
}
