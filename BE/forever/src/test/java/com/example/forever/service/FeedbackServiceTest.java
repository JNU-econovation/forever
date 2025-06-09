package com.example.forever.service;

import com.example.forever.common.annotation.MemberInfo;
import com.example.forever.common.validator.MemberValidator;
import com.example.forever.domain.Feedback;
import com.example.forever.domain.FeedbackContent;
import com.example.forever.domain.Member;
import com.example.forever.dto.feedback.FeedbackRequest;
import com.example.forever.repository.FeedbackRepository;
import com.example.forever.repository.FeedbackContentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("FeedbackService 단위 테스트")
class FeedbackServiceTest {

    @Mock
    private FeedbackRepository feedbackRepository;

    @Mock
    private FeedbackContentRepository feedbackContentRepository;

    @Mock
    private MemberValidator memberValidator;

    @Mock
    private DiscordWebhookService discordWebhookService;

    @InjectMocks
    private FeedbackService feedbackService;

    @Test
    @DisplayName("피드백 저장 성공 - 여러 내용")
    void saveFeedback_Success_MultipleContents() {
        // given
        Long memberId = 1L;
        MemberInfo memberInfo = new MemberInfo(memberId);
        Member member = createMember(memberId);
        
        List<String> contents = List.of("요약이 아쉬워요", "시간이 너무 길어요", "기타 : 더 자세한 설명이 필요해요");
        FeedbackRequest request = new FeedbackRequest("after_summary_create", contents, 3);
        
        Feedback savedFeedback = createFeedbackWithContents("after_summary_create", 3, member, contents);
        
        when(memberValidator.validateAndGetById(memberId)).thenReturn(member);
        when(feedbackRepository.save(any(Feedback.class))).thenReturn(savedFeedback);

        // when
        feedbackService.saveFeedback(request, memberInfo);

        // then
        verify(memberValidator).validateAndGetById(memberId);
        verify(feedbackRepository).save(any(Feedback.class));
        verify(discordWebhookService).sendFeedbackNotification(any(Feedback.class));
    }

    @Test
    @DisplayName("피드백 저장 성공 - 단일 내용")
    void saveFeedback_Success_SingleContent() {
        // given
        Long memberId = 1L;
        MemberInfo memberInfo = new MemberInfo(memberId);
        Member member = createMember(memberId);
        
        List<String> contents = List.of("질문이 부정확해요");
        FeedbackRequest request = new FeedbackRequest("after_question_create", contents, 7);
        
        Feedback savedFeedback = createFeedbackWithContents("after_question_create", 7, member, contents);
        
        when(memberValidator.validateAndGetById(memberId)).thenReturn(member);
        when(feedbackRepository.save(any(Feedback.class))).thenReturn(savedFeedback);

        // when
        feedbackService.saveFeedback(request, memberInfo);

        // then
        verify(memberValidator).validateAndGetById(memberId);
        verify(feedbackRepository).save(any(Feedback.class));
        verify(discordWebhookService).sendFeedbackNotification(any(Feedback.class));
    }

    @Test
    @DisplayName("피드백 저장 성공 - 최고 평점")
    void saveFeedback_Success_MaxRating() {
        // given
        Long memberId = 1L;
        MemberInfo memberInfo = new MemberInfo(memberId);
        Member member = createMember(memberId);
        
        List<String> contents = List.of("완벽해요!", "최고의 서비스입니다");
        FeedbackRequest request = new FeedbackRequest("general", contents, 10);
        
        Feedback savedFeedback = createFeedbackWithContents("general", 10, member, contents);
        
        when(memberValidator.validateAndGetById(memberId)).thenReturn(member);
        when(feedbackRepository.save(any(Feedback.class))).thenReturn(savedFeedback);

        // when
        feedbackService.saveFeedback(request, memberInfo);

        // then
        verify(memberValidator).validateAndGetById(memberId);
        verify(feedbackRepository).save(any(Feedback.class));
        verify(discordWebhookService).sendFeedbackNotification(any(Feedback.class));
    }

    private Member createMember(Long memberId) {
        return Member.builder()
                .id(memberId)
                .email("test@example.com")
                .nickname("테스트회원")
                .build();
    }

    private Feedback createFeedbackWithContents(String position, Integer rating, Member member, List<String> contentStrings) {
        Feedback feedback = Feedback.builder()
                .id(1L)
                .position(position)
                .rating(rating)
                .member(member)
                .build();
        
        // FeedbackContent 객체들을 생성하여 추가
        for (String contentString : contentStrings) {
            FeedbackContent feedbackContent = FeedbackContent.builder()
                    .feedback(feedback)
                    .content(contentString)
                    .build();
            feedback.getContents().add(feedbackContent);
        }
        
        return feedback;
    }
}
