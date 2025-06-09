package com.example.forever.service;

import com.example.forever.common.annotation.MemberInfo;
import com.example.forever.common.validator.MemberValidator;
import com.example.forever.domain.Feedback;
import com.example.forever.domain.Member;
import com.example.forever.dto.feedback.FeedbackRequest;
import com.example.forever.dto.feedback.FeedbackResponse;
import com.example.forever.repository.FeedbackRepository;
import com.example.forever.repository.FeedbackContentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final FeedbackContentRepository feedbackContentRepository;
    private final MemberValidator memberValidator;
    private final DiscordWebhookService discordWebhookService;

    /**
     * 피드백 저장
     */
    @Transactional
    public void saveFeedback(FeedbackRequest request, MemberInfo memberInfo) {
        Member member = memberValidator.validateAndGetById(memberInfo.getMemberId());

        Feedback feedback = Feedback.create(
                request.position(),
                request.rating(),
                member,
                request.content()
        );

        Feedback savedFeedback = feedbackRepository.save(feedback);
        
        // Discord 웹훅을 통해 알림 전송
        discordWebhookService.sendFeedbackNotification(savedFeedback);

        log.info("피드백이 저장되었습니다. 위치: {}, 평점: {}, 내용 개수: {}", 
                request.position(), request.rating(), request.content().size());
    }

    /**
     * 피드백 목록 조회 (관리자용)
     */
    @Transactional(readOnly = true)
    public List<FeedbackResponse> getAllFeedbacks() {
        return feedbackRepository.findAll().stream()
                .map(FeedbackResponse::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * 위치별 피드백 조회
     */
    @Transactional(readOnly = true)
    public List<FeedbackResponse> getFeedbacksByPosition(String position) {
        return feedbackRepository.findByPositionOrderByCreatedAtDesc(position).stream()
                .map(FeedbackResponse::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * 피드백 처리 상태 변경 (관리자용)
     */
    @Transactional
    public FeedbackResponse updateFeedbackProcessed(Long feedbackId, boolean processed) {
        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new IllegalArgumentException("피드백을 찾을 수 없습니다."));
        
        feedback.setProcessed(processed);
        
        return FeedbackResponse.fromEntity(feedback);
    }
}
