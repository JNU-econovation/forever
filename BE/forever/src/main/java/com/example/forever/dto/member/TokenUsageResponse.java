package com.example.forever.dto.member;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 토큰 사용량 조회 응답 DTO
 */
public record TokenUsageResponse(
        @JsonProperty("remain_usage")
        Integer remainUsage
) {
}
