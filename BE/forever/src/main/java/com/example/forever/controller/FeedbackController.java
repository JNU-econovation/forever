package com.example.forever.controller;

import com.example.forever.common.annotation.AuthMember;
import com.example.forever.common.annotation.MemberInfo;
import com.example.forever.common.response.ApiResponse;
import com.example.forever.common.response.ApiResponseGenerator;
import com.example.forever.dto.feedback.FeedbackRequest;
import com.example.forever.dto.feedback.FeedbackResponse;
import com.example.forever.service.FeedbackService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    /**
     * 피드백 제출
     */
    @PostMapping
    public ApiResponse<ApiResponse.SuccesCustomBody<Void>> submitFeedback(
            @Valid @RequestBody FeedbackRequest request,
            @AuthMember MemberInfo memberInfo) {
        
        feedbackService.saveFeedback(request, memberInfo);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

    /**
     * 모든 피드백 조회 (관리자용)
     */
    @GetMapping("/admin/all")
    public ResponseEntity<List<FeedbackResponse>> getAllFeedbacks() {
        // 실제 구현에서는 관리자 권한 체크 필요
        List<FeedbackResponse> feedbacks = feedbackService.getAllFeedbacks();
        return ResponseEntity.ok(feedbacks);
    }

    /**
     * 위치별 피드백 조회
     */
    @GetMapping("/position/{position}")
    public ResponseEntity<List<FeedbackResponse>> getFeedbacksByPosition(
            @PathVariable String position) {
        // 실제 구현에서는 권한 체크 필요
        List<FeedbackResponse> feedbacks = feedbackService.getFeedbacksByPosition(position);
        return ResponseEntity.ok(feedbacks);
    }

    /**
     * 피드백 처리 상태 변경 (관리자용)
     */
    @PatchMapping("/admin/{feedbackId}/processed")
    public ResponseEntity<FeedbackResponse> updateFeedbackProcessed(
            @PathVariable Long feedbackId,
            @RequestParam boolean processed) {
        // 실제 구현에서는 관리자 권한 체크 필요
        FeedbackResponse response = feedbackService.updateFeedbackProcessed(feedbackId, processed);
        return ResponseEntity.ok(response);
    }
}
