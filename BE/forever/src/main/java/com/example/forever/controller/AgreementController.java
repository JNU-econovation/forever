package com.example.forever.controller;


import com.example.forever.common.annotation.AuthMember;
import com.example.forever.common.annotation.MemberInfo;
import com.example.forever.common.response.ApiResponse;
import com.example.forever.common.response.ApiResponseGenerator;
import com.example.forever.dto.AgreementTermsResponse;
import com.example.forever.dto.document.request.SaveQuestionAnswerRequest;
import com.example.forever.service.AgreementService;
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
public class AgreementController {

    private final AgreementService agreementService;

    @PatchMapping("/terms")
    public ApiResponse<ApiResponse.SuccesCustomBody<Void>> agreeToTerms(@AuthMember MemberInfo memberInfo){
        agreementService.agreeToTerms(memberInfo);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

    @GetMapping("/terms")
    public ApiResponse<ApiResponse.SuccesCustomBody<AgreementTermsResponse>> isTermsAgreed(@AuthMember MemberInfo memberInfo){
        AgreementTermsResponse response = agreementService.isTermsAgreed(memberInfo);
        return ApiResponseGenerator.success(response, HttpStatus.OK);
    }

    @PatchMapping("/policy")
    public ApiResponse<ApiResponse.SuccesCustomBody<Void>> agreeToPolicy(@AuthMember MemberInfo memberInfo){
        agreementService.agreeToPolicy(memberInfo);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

    @GetMapping("/policy")
    public ApiResponse<ApiResponse.SuccesCustomBody<AgreementTermsResponse>> isPolicyAgreed(@AuthMember MemberInfo memberInfo){
        AgreementTermsResponse response = agreementService.isPolicyAgreed(memberInfo);
        return ApiResponseGenerator.success(response, HttpStatus.OK);
    }


}
