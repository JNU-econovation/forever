package com.example.forever.service;

import com.example.forever.domain.Feedback;
import com.example.forever.domain.FeedbackContent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DiscordWebhookService {

    private final RestTemplate restTemplate;
    
    @Value("${discord.webhook.url}")
    private String webhookUrl;

    public DiscordWebhookService() {
        this.restTemplate = new RestTemplate();
    }

    public void sendFeedbackNotification(Feedback feedback) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 임베드 메시지 구성
        Map<String, Object> embed = new HashMap<>();
        embed.put("title", "📝 새로운 피드백 도착!");
        embed.put("color", 3447003); // Discord 파란색
        embed.put("timestamp", feedback.getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "Z");

        // 필드 구성
        List<Map<String, Object>> fields = new ArrayList<>();
        
        // 발생 위치 필드
        Map<String, Object> positionField = new HashMap<>();
        positionField.put("name", "📍 발생 위치");
        positionField.put("value", feedback.getPosition());
        positionField.put("inline", true);
        fields.add(positionField);

        // 평점 필드 (별점으로 시각화)
        Map<String, Object> ratingField = new HashMap<>();
        ratingField.put("name", "⭐ 평점");
        ratingField.put("value", generateStarRating(feedback.getRating()));
        ratingField.put("inline", true);
        fields.add(ratingField);

        // 피드백 내용 필드
        Map<String, Object> contentField = new HashMap<>();
        contentField.put("name", "💬 피드백 내용");
        contentField.put("value", formatFeedbackContents(feedback.getContents()));
        contentField.put("inline", false);
        fields.add(contentField);

        embed.put("fields", fields);

        // 푸터 설정
        Map<String, Object> footer = new HashMap<>();
        footer.put("text", "Forever 피드백 시스템");
        embed.put("footer", footer);

        // 웹훅 메시지 구성
        Map<String, Object> message = new HashMap<>();
        message.put("embeds", new Object[]{embed});

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(message, headers);
        
        try {
            restTemplate.postForObject(webhookUrl, request, String.class);
        } catch (Exception e) {
            // 디스코드 웹훅 전송 실패 로깅
            System.err.println("Discord webhook 전송 실패: " + e.getMessage());
        }
    }

    /**
     * 평점을 별점으로 시각화
     */
    private String generateStarRating(int rating) {
        StringBuilder stars = new StringBuilder();
        for (int i = 0; i < rating; i++) {
            stars.append("⭐");
        }
        return String.format("%s (%d/10)", stars.toString(), rating);
    }

    /**
     * 피드백 내용들을 포맷팅
     */
    private String formatFeedbackContents(List<FeedbackContent> contents) {
        if (contents.isEmpty()) {
            return "";
        }

        StringBuilder formatted = new StringBuilder();
        for (FeedbackContent content : contents) {
            formatted.append("• ").append(content.getContent()).append("\n");
        }
        
        // 마지막 줄바꿈 제거
        if (formatted.length() > 0) {
            formatted.setLength(formatted.length() - 1);
        }
        
        return formatted.toString();
    }
}
