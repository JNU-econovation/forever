package com.example.forever.application.member;

import java.util.List;

public record SignUpCommand(
        String name,
        String major,
        String school,
        String email,
        List<String> inflow,
        Boolean marketingAgreement
) {
}
