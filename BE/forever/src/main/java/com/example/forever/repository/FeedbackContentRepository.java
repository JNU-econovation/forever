package com.example.forever.repository;

import com.example.forever.domain.FeedbackContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeedbackContentRepository extends JpaRepository<FeedbackContent, Long> {
    
    /**
     * 특정 피드백의 모든 내용 조회
     */
    List<FeedbackContent> findByFeedbackIdOrderByCreatedAtAsc(Long feedbackId);
    
    /**
     * 특정 피드백 ID들의 모든 내용 조회
     */
    @Query("SELECT fc FROM FeedbackContent fc WHERE fc.feedback.id IN :feedbackIds ORDER BY fc.feedback.id, fc.createdAt")
    List<FeedbackContent> findByFeedbackIdsOrderByFeedbackIdAndCreatedAt(@Param("feedbackIds") List<Long> feedbackIds);
}
