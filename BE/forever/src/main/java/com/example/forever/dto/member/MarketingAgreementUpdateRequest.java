package com.example.forever.dto.member;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

/**
 * 마케팅 동의 변경 요청 DTO
 */
public record MarketingAgreementUpdateRequest(
        @JsonProperty("is_agreement")
        @NotNull(message = "마케팅 동의 여부는 필수입니다.")
        Boolean isAgreement
) {
}
