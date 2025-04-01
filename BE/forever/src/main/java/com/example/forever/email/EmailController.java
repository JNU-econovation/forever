package com.example.forever.email;

import com.example.forever.common.response.ApiResponse;
import com.example.forever.common.response.ApiResponseGenerator;
import com.example.forever.dto.document.response.DocumentListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/email")
public class EmailController {

    private final EmailSendService emailSendService;

    //인증 번호 전송
    @PostMapping("/verification")
    public ApiResponse<ApiResponse.SuccesCustomBody<Void>> sendEmail(@RequestBody MemberEmailRequest request) {
        emailSendService.sendCodeToEmail(request.email());
        return ApiResponseGenerator.success(HttpStatus.OK);
    }


    //이메일 인증
    @PostMapping("/verify-code")
    public ApiResponse<ApiResponse.SuccesCustomBody<Void>> verifyEmail(@RequestBody MemberEmailVerifyRequest request) {
        emailSendService.verifyCode(request.email(), request.verificationCode());
        return ApiResponseGenerator.success(HttpStatus.OK);
    }
}
