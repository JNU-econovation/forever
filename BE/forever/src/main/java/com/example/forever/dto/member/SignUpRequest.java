package com.example.forever.dto.member;

import java.util.List;

public record SignUpRequest(
        String name,
        String major,
        String school,
        String email,
        List<String> inflow
) {
}
