package com.example.forever.controller;

import com.example.forever.common.annotation.AuthMember;
import com.example.forever.common.annotation.MemberInfo;
import com.example.forever.common.response.ApiResponse;
import com.example.forever.common.response.ApiResponseGenerator;
import com.example.forever.dto.AiTokenUsageResponse;
import com.example.forever.service.AiUsageCheckService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "AI 사용량 관리", description = "AI 기능 사용량 및 업로드 상태 확인 API")
public class AiUsageCheckController {

    private final AiUsageCheckService aiUsageCheckService;

    @PostMapping("/upload/isAvailable")
    @Operation(summary = "업로드 가능 여부 확인", description = "현재 사용자가 문서 업로드가 가능한지 AI 토큰 사용량을 확인합니다.")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "업로드 가능 여부 확인 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증되지 않은 사용자"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "사용 가능한 토큰이 없음"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "존재하지 않는 회원")

    })
    public ApiResponse<ApiResponse.SuccesCustomBody<AiTokenUsageResponse>> checkTokenUsage(
            @Parameter(hidden = true) @AuthMember MemberInfo memberInfo){
        AiTokenUsageResponse response = aiUsageCheckService.checkTokenUsage(memberInfo);
        return ApiResponseGenerator.success(response, HttpStatus.OK);
    }

    @PostMapping("/summary/complete")
    @Operation(summary = "요약 완료 확인", description = "문서 요약이 완료되었음을 확인하고 AI 토큰 사용량을 차감합니다.")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요약 완료 처리 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증되지 않은 사용자"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "존재하지 않는 회원")
    })
    public ApiResponse<ApiResponse.SuccesCustomBody<Void>> checkCompleteUpload(
            @Parameter(hidden = true) @AuthMember MemberInfo memberInfo){
        aiUsageCheckService.checkCompleteUpload(memberInfo);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }


}
