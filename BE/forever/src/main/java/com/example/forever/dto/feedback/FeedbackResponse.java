package com.example.forever.dto.feedback;

import com.example.forever.domain.Feedback;
import com.example.forever.domain.FeedbackContent;

import java.time.LocalDateTime;
import java.util.List;

public record FeedbackResponse(
        Long id,
        String position,
        List<String> content,
        Integer rating,
        boolean processed,
        LocalDateTime createdAt
) {
    public static FeedbackResponse fromEntity(Feedback feedback) {
        List<String> contentList = feedback.getContents().stream()
                .map(FeedbackContent::getContent)
                .toList();
        
        return new FeedbackResponse(
                feedback.getId(),
                feedback.getPosition(),
                contentList,
                feedback.getRating(),
                feedback.isProcessed(),
                feedback.getCreatedAt()
        );
    }
}
