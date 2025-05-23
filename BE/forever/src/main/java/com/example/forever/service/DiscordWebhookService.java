package com.example.forever.service;

import com.example.forever.domain.Feedback;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
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

        Map<String, Object> embed = new HashMap<>();
        embed.put("title", "새로운 피드백이 등록되었습니다!");
        embed.put("color", 0x00FFFF); // 밝은 청록색
        
        // 피드백 정보
        String description = String.format(
                "**위치**: %s\n**평점**: %d/10\n**내용**: %s\n**시간**: %s",
                feedback.getPosition(),
                feedback.getRating(),
                feedback.getContent(),
                feedback.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
        embed.put("description", description);

        // 푸터 설정
        Map<String, String> footer = new HashMap<>();
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
}
