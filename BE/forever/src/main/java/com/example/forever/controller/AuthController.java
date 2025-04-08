package com.example.forever.controller;

import com.example.forever.common.annotation.AuthMember;
import com.example.forever.common.annotation.MemberInfo;
import com.example.forever.common.response.ApiResponse;
import com.example.forever.common.response.ApiResponseGenerator;
import com.example.forever.dto.KakaoLoginResponse;
import com.example.forever.dto.member.LoginTokenResponse;
import com.example.forever.dto.member.SignUpRequest;
import com.example.forever.service.KakaoAuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/oauth")
@RequiredArgsConstructor
public class AuthController {

    private final KakaoAuthService kakaoAuthService;


    @GetMapping("/kakao")
    public ApiResponse<ApiResponse.SuccesCustomBody<KakaoLoginResponse>> oAuthLogin(
            @RequestParam(value = "code") String code, HttpServletResponse resp) {
        KakaoLoginResponse response = kakaoAuthService.kakaoLogin(code, resp);
        return ApiResponseGenerator.success(response, HttpStatus.OK);
    }

    @PostMapping("/kakao/signup")
    public ApiResponse<ApiResponse.SuccesCustomBody<Void>> oAuthSignup(@RequestBody SignUpRequest request, HttpServletResponse response) {
        kakaoAuthService.kakaoSignUp(request, response);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

    @PostMapping("/quit")
    public ApiResponse<ApiResponse.SuccesCustomBody<Void>> oAuthQuit(@AuthMember MemberInfo memberInfo) {
        kakaoAuthService.kakaoQuit(memberInfo.getMemberId());
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

    @GetMapping("/refresh")
    public ApiResponse<ApiResponse.SuccesCustomBody<Void>> refreshToken(@CookieValue(value = "refresh_token", required = false) String refreshToken, HttpServletResponse resp) {
        kakaoAuthService.refreshToken(refreshToken, resp);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }




}
