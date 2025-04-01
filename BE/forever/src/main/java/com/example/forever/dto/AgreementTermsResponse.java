package com.example.forever.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public record AgreementTermsResponse(
        @JsonProperty("is_agreement")
        Boolean isAgreement,
        @JsonProperty("effective_date")
        String effectiveDate
) {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yy-MM-dd");

    public AgreementTermsResponse(Boolean isAgreement, LocalDate effectiveDate) {
        this(isAgreement, effectiveDate.format(FORMATTER));
    }
}