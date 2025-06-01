package com.example.forever.controller;


import com.example.forever.common.annotation.AuthMember;
import com.example.forever.common.annotation.MemberInfo;
import com.example.forever.common.response.ApiResponse;
import com.example.forever.common.response.ApiResponseGenerator;
import com.example.forever.dto.AgreementTermsResponse;
import com.example.forever.dto.document.request.SaveQuestionAnswerRequest;
import com.example.forever.service.AgreementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/agreement")
@RequiredArgsConstructor
@Tag(name = "약관/정책 동의", description = "서비스 이용약관 및 개인정보처리방침 동의 관리 API")
public class AgreementController {

    private final AgreementService agreementService;

    @PatchMapping("/terms")
    @Operation(summary = "약관 동의", description = "서비스 이용약관에 동의합니다.")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "약관 동의 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증되지 않은 사용자"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "존재하지 않는 회원")
    })
    public ApiResponse<ApiResponse.SuccesCustomBody<Void>> agreeToTerms(
            @Parameter(hidden = true) @AuthMember MemberInfo memberInfo){
        agreementService.agreeToTerms(memberInfo);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

    @GetMapping("/terms")
    @Operation(summary = "약관 동의 상태 조회", description = "현재 사용자의 서비스 이용약관 동의 상태를 조회합니다.")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "약관 동의 상태 조회 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증되지 않은 사용자"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "존재하지 않는 회원")
    })
    public ApiResponse<ApiResponse.SuccesCustomBody<AgreementTermsResponse>> isTermsAgreed(
            @Parameter(hidden = true) @AuthMember MemberInfo memberInfo){
        AgreementTermsResponse response = agreementService.isTermsAgreed(memberInfo);
        return ApiResponseGenerator.success(response, HttpStatus.OK);
    }

    @PatchMapping("/policy")
    @Operation(summary = "정책 동의", description = "개인정보처리방침에 동의합니다.")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "정책 동의 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증되지 않은 사용자"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "존재하지 않는 회원")
    })
    public ApiResponse<ApiResponse.SuccesCustomBody<Void>> agreeToPolicy(
            @Parameter(hidden = true) @AuthMember MemberInfo memberInfo){
        agreementService.agreeToPolicy(memberInfo);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

    @GetMapping("/policy")
    @Operation(summary = "정책 동의 상태 조회", description = "현재 사용자의 개인정보처리방침 동의 상태를 조회합니다.")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "정책 동의 상태 조회 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증되지 않은 사용자"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "존재하지 않는 회원")
    })
    public ApiResponse<ApiResponse.SuccesCustomBody<AgreementTermsResponse>> isPolicyAgreed(
            @Parameter(hidden = true) @AuthMember MemberInfo memberInfo){
        AgreementTermsResponse response = agreementService.isPolicyAgreed(memberInfo);
        return ApiResponseGenerator.success(response, HttpStatus.OK);
    }


}
