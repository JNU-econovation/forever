package com.example.forever.dto.member;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record SignUpRequest(
        String name,
        String major,
        String school,
        String email,
        List<String> inflow,
        @JsonProperty("marketing_agreement")
        Boolean marketingAgreement
) {
}
