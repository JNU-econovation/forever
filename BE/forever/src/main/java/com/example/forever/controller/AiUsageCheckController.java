package com.example.forever.controller;

import com.example.forever.common.annotation.AuthMember;
import com.example.forever.common.annotation.MemberInfo;
import com.example.forever.common.response.ApiResponse;
import com.example.forever.common.response.ApiResponseGenerator;
import com.example.forever.dto.AiTokenUsageResponse;
import com.example.forever.service.AiUsageCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AiUsageCheckController {

    private final AiUsageCheckService aiUsageCheckService;

    @PostMapping("/upload/isAvailable")
    public ApiResponse<ApiResponse.SuccesCustomBody<AiTokenUsageResponse>> checkTokenUsage(@AuthMember MemberInfo memberInfo){
        AiTokenUsageResponse response = aiUsageCheckService.checkTokenUsage(memberInfo);
        return ApiResponseGenerator.success(response, HttpStatus.OK);
    }

    @PostMapping("summary/complete")
    public ApiResponse<ApiResponse.SuccesCustomBody<Void>> checkCompleteUpload(@AuthMember MemberInfo memberInfo){
        aiUsageCheckService.checkCompleteUpload(memberInfo);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }


}
