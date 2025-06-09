package com.example.forever.controller;


import com.example.forever.common.annotation.AuthMember;
import com.example.forever.common.annotation.MemberInfo;
import com.example.forever.common.response.ApiResponse;
import com.example.forever.common.response.ApiResponseGenerator;
import com.example.forever.dto.AgreementTermsResponse;
import com.example.forever.dto.member.MarketingAgreementResponse;
import com.example.forever.dto.member.MarketingAgreementUpdateRequest;
import com.example.forever.application.member.MarketingAgreementApplicationService;
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
    private final MarketingAgreementApplicationService marketingAgreementApplicationService;

    @PatchMapping("/terms")
    @Operation(summary = "약관 동의", description = "서비스 이용약관에 동의합니다.")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "약관 동의 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증되지 않은 사용자"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "존재하지 않는 회원")
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
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "존재하지 않는 회원")
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
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "존재하지 않는 회원")
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
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "존재하지 않는 회원")
    })
    public ApiResponse<ApiResponse.SuccesCustomBody<AgreementTermsResponse>> isPolicyAgreed(
            @Parameter(hidden = true) @AuthMember MemberInfo memberInfo){
        AgreementTermsResponse response = agreementService.isPolicyAgreed(memberInfo);
        return ApiResponseGenerator.success(response, HttpStatus.OK);
    }

    @GetMapping("/marketing")
    @Operation(summary = "마케팅 동의 상태 조회", description = "현재 사용자의 마케팅 동의 상태를 조회합니다.")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "마케팅 동의 상태 조회 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증되지 않은 사용자"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "존재하지 않는 회원")
    })
    public ApiResponse<ApiResponse.SuccesCustomBody<MarketingAgreementResponse>> getMarketingAgreement(
            @Parameter(hidden = true) @AuthMember MemberInfo memberInfo) {
        MarketingAgreementResponse response = marketingAgreementApplicationService
                .getMarketingAgreement(memberInfo.getMemberId());
        return ApiResponseGenerator.success(response, HttpStatus.OK);
    }

    @PatchMapping("/marketing")
    @Operation(summary = "마케팅 동의 상태 변경", description = "현재 사용자의 마케팅 동의 상태를 변경합니다.")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "마케팅 동의 상태 변경 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증되지 않은 사용자"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "존재하지 않는 회원"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청 데이터")
    })
    public ApiResponse<ApiResponse.SuccesCustomBody<MarketingAgreementResponse>> updateMarketingAgreement(
            @Parameter(hidden = true) @AuthMember MemberInfo memberInfo,
            @Parameter(description = "마케팅 동의 변경 요청", required = true)
            @Valid @RequestBody MarketingAgreementUpdateRequest request) {
        MarketingAgreementResponse response = marketingAgreementApplicationService
                .updateMarketingAgreement(memberInfo.getMemberId(), request);
        return ApiResponseGenerator.success(response, HttpStatus.OK);
    }


}
