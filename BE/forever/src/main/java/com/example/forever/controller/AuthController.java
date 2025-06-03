package com.example.forever.controller;

import com.example.forever.common.annotation.AuthMember;
import com.example.forever.common.annotation.MemberInfo;
import com.example.forever.common.response.ApiResponse;
import com.example.forever.common.response.ApiResponseGenerator;
import com.example.forever.dto.KakaoLoginResponse;
import com.example.forever.dto.member.LoginTokenResponse;
import com.example.forever.dto.member.SignUpRequest;
import com.example.forever.application.member.SignUpCommand;
import com.example.forever.application.member.MemberApplicationService;
import com.example.forever.service.KakaoAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "인증", description = "카카오 OAuth 기반 인증 API")
public class AuthController {

    private final KakaoAuthService kakaoAuthService;
    private final MemberApplicationService memberApplicationService;

    @GetMapping("/kakao")
    @Operation(summary = "카카오 로그인", description = "카카오 인가 코드를 통해 로그인을 진행합니다.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "로그인 성공 또는 회원가입 필요"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "유효하지 않은 인가코드"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "탈퇴한 회원")
    })
    public ApiResponse<ApiResponse.SuccesCustomBody<KakaoLoginResponse>> oAuthLogin(
            @Parameter(description = "카카오에서 발급받은 인가 코드", required = true)
            @RequestParam(value = "code") String code, 
            HttpServletResponse resp) {
        KakaoLoginResponse response = kakaoAuthService.kakaoLogin(code, resp);
        return ApiResponseGenerator.success(response, HttpStatus.OK);
    }

    @PostMapping("/kakao/signup")
    @Operation(summary = "회원가입", description = "카카오 OAuth를 통한 회원가입을 진행합니다.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "회원가입 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "이미 존재하는 이메일 또는 잘못된 요청 데이터")
    })
    public ApiResponse<ApiResponse.SuccesCustomBody<Void>> oAuthSignup(
            @Parameter(description = "회원가입 요청 데이터", required = true)
            @RequestBody SignUpRequest request, 
            HttpServletResponse response) {
        // DTO를 Command로 변환
        SignUpCommand command = new SignUpCommand(
                request.name(),
                request.major(),
                request.school(),
                request.email(),
                request.inflow()
        );
        
        memberApplicationService.signUp(command, response);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

    @PostMapping("/quit")
    @Operation(summary = "회원탈퇴", description = "현재 로그인된 사용자의 회원탈퇴를 진행합니다.")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "회원탈퇴 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증되지 않은 사용자"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "존재하지 않는 회원")
    })
    public ApiResponse<ApiResponse.SuccesCustomBody<Void>> oAuthQuit(
            @Parameter(hidden = true) @AuthMember MemberInfo memberInfo) {
        kakaoAuthService.kakaoQuit(memberInfo.getMemberId());
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

    @PostMapping("/refresh")
    @Operation(summary = "토큰 재발급", description = "리프레시 토큰을 사용하여 액세스 토큰을 재발급합니다.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "토큰 재발급 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "유효하지 않은 리프레시 토큰")
    })
    public ApiResponse<ApiResponse.SuccesCustomBody<Void>> refreshToken(
            @Parameter(description = "리프레시 토큰 (쿠키에서 자동 추출)")
            @CookieValue(value = "refresh_token", required = false) String refreshToken, 
            HttpServletResponse resp) {
        kakaoAuthService.refreshToken(refreshToken, resp);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

}
