package com.example.forever.dto.feedback;

import jakarta.validation.constraints.*;

import java.util.List;

public record FeedbackRequest(
        @NotBlank(message = "피드백 위치는 필수입니다.")
        String position,
        
        @NotEmpty(message = "피드백 내용은 최소 1개 이상이어야 합니다.")
        @Size(max = 10, message = "피드백 내용은 최대 10개까지 선택 가능합니다.")
        List<@NotBlank(message = "피드백 내용은 비어있을 수 없습니다.") 
             @Size(max = 500, message = "각 피드백 내용은 500자 이내로 작성해주세요.") 
             String> content,
        
        @NotNull(message = "평점은 필수입니다.")
        @Min(value = 1, message = "평점은 최소 1점 이상이어야 합니다.")
        @Max(value = 10, message = "평점은 최대 10점까지 가능합니다.")
        Integer rating
) {
}
