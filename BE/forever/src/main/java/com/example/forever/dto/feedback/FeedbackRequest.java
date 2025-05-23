package com.example.forever.dto.feedback;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FeedbackRequest(
        @NotBlank(message = "피드백 위치는 필수입니다.")
        String position,
        
        String content,
        
        @NotNull(message = "평점은 필수입니다.")
        @Min(value = 1, message = "평점은 최소 1점 이상이어야 합니다.")
        @Max(value = 10, message = "평점은 최대 10점까지 가능합니다.")
        Integer rating
) {
}
