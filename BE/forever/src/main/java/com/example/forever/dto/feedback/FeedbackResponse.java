package com.example.forever.dto.feedback;

import com.example.forever.domain.Feedback;

import java.time.LocalDateTime;

public record FeedbackResponse(
        Long id,
        String position,
        String content,
        Integer rating,
        boolean processed,
        LocalDateTime createdAt
) {
    public static FeedbackResponse fromEntity(Feedback feedback) {
        return new FeedbackResponse(
                feedback.getId(),
                feedback.getPosition(),
                feedback.getContent(),
                feedback.getRating(),
                feedback.isProcessed(),
                feedback.getCreatedAt()
        );
    }
}
