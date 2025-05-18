package com.example.forever.controller;

import com.example.forever.common.annotation.MemberInfo;
import com.example.forever.common.test.TestMemberArgumentResolver;
import com.example.forever.dto.feedback.FeedbackRequest;
import com.example.forever.dto.feedback.FeedbackResponse;
import com.example.forever.service.FeedbackService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class FeedbackControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private FeedbackService feedbackService;

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private FeedbackController feedbackController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(feedbackController)
                .setCustomArgumentResolvers(new TestMemberArgumentResolver())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("피드백 제출 테스트")
    void submitFeedback_Success() throws Exception {
        // Given
        FeedbackRequest request = new FeedbackRequest(
                "after_summary_create",
                "매우 유용한 기능입니다.",
                5
        );

        FeedbackResponse response = new FeedbackResponse(
                1L,
                "after_summary_create",
                "매우 유용한 기능입니다.",
                5,
                false,
                LocalDateTime.now()
        );

        when(feedbackService.saveFeedback(any(FeedbackRequest.class), any(MemberInfo.class)))
                .thenReturn(response);

        // When & Then
        mockMvc.perform(post("/api/feedback")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("위치별 피드백 조회 테스트")
    void getFeedbacksByPosition_Success() throws Exception {
        // Given
        String position = "after_summary_create";
        
        FeedbackResponse response = new FeedbackResponse(
                1L,
                position,
                "매우 유용한 기능입니다.",
                5,
                false,
                LocalDateTime.now()
        );

        when(feedbackService.getFeedbacksByPosition(eq(position)))
                .thenReturn(List.of(response));

        // When & Then
        mockMvc.perform(get("/api/feedback/position/{position}", position))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].position").value(position))
                .andExpect(jsonPath("$[0].rating").value(5));
    }
}
