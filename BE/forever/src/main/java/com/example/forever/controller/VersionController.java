package com.example.forever.controller;

import com.example.forever.common.annotation.AuthMember;
import com.example.forever.common.annotation.MemberInfo;
import com.example.forever.common.response.ApiResponse;
import com.example.forever.common.response.ApiResponseGenerator;
import com.example.forever.common.validator.MemberValidator;
import com.example.forever.domain.AppInfo;
import com.example.forever.domain.Member;
import com.example.forever.dto.AppVersionResponse;
import com.example.forever.dto.document.request.DocumentUpdateRequest;
import com.example.forever.repository.AppInfoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class VersionController {

    private final AppInfoRepository appInfoRepository;

    @GetMapping("/version")
    public ApiResponse<ApiResponse.SuccesCustomBody<AppVersionResponse>> getVersion() {
        AppInfo appInfo = appInfoRepository.findById(1L).orElseThrow();
        AppVersionResponse appVersionResponse = new AppVersionResponse(appInfo.getLatestVersion(),
                appInfo.getStoreUrl());
        return ApiResponseGenerator.success(appVersionResponse, HttpStatus.OK);
    }

}
