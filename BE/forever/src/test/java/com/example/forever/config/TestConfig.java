package com.example.forever.config;

import com.example.forever.common.auth.JwtTokenProvider;
import com.example.forever.common.feign.kakao.client.KakaoInfoClient;
import com.example.forever.common.feign.kakao.client.KakaoTokenClient;
import com.example.forever.common.feign.kakao.client.KakaoUnlinkClient;
import com.example.forever.service.DiscordWebhookService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * 테스트 환경에서 사용할 빈 구성
 */
@TestConfiguration
@Profile("test")
public class TestConfig {
    
    /**
     * 테스트용 JwtTokenProvider 빈
     */
    @Bean
    @Primary
    public JwtTokenProvider jwtTokenProvider() {
        return new JwtTokenProvider();
    }
    /**
     * 외부 API 클라이언트들은 Mock으로 처리
     */
    @MockBean
    private KakaoTokenClient kakaoTokenClient;
    
    @MockBean
    private KakaoInfoClient kakaoInfoClient;
    
    @MockBean
    private KakaoUnlinkClient kakaoUnlinkClient;
    
    @MockBean
    private DiscordWebhookService discordWebhookService;
}
