package com.example.forever.controller;

import com.example.forever.common.response.ApiResponse;
import com.example.forever.common.response.ApiResponseGenerator;
import com.example.forever.dto.member.LoginTokenResponse;
import com.example.forever.service.KakaoAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/oauth")
@RequiredArgsConstructor
public class AuthController {

    private final KakaoAuthService kakaoAuthService;


    @GetMapping("/kakao")
    public ApiResponse<ApiResponse.SuccesCustomBody<LoginTokenResponse>> oAuthLogin(
            @RequestParam(value = "code") String code) {
        LoginTokenResponse response = kakaoAuthService.kakaoLogin(code);
        return ApiResponseGenerator.success(response, HttpStatus.OK);
    }
}
