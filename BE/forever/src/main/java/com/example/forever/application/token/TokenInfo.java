package com.example.forever.application.token;

import java.time.LocalDate;

public record TokenInfo(
        int availableTokens,
        int totalUsageCount,
        LocalDate lastRefreshDate
) {
    
    public static TokenInfo of(int availableTokens, int totalUsageCount, LocalDate lastRefreshDate) {
        return new TokenInfo(availableTokens, totalUsageCount, lastRefreshDate);
    }
}
