package com.example.forever.config;

import com.example.forever.common.auth.JwtTokenProvider;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
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
    public JwtTokenProvider jwtTokenProvider() {
        return new JwtTokenProvider();
    }
    
    /**
     * 테스트용 JavaMailSender 빈
     */
    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("localhost");
        mailSender.setPort(3025);
        mailSender.setUsername("test");
        mailSender.setPassword("test");
        return mailSender;
    }
}
