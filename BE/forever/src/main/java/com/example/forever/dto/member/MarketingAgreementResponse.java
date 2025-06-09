package com.example.forever.dto.member;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 마케팅 동의 조회 응답 DTO
 */
public record MarketingAgreementResponse(
        @JsonProperty("is_agreement")
        Boolean isAgreement,
        
        @JsonProperty("effective_date")
        String effectiveDate
) {
}
