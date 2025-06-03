package com.example.forever.infrastructure.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationResponseBuilder {
    
    private static final int REFRESH_TOKEN_MAX_AGE = 7 * 24 * 60 * 60; // 7일
    
    public void setAuthenticationResponse(HttpServletResponse response, 
                                        String accessToken, 
                                        String refreshToken) {
        // Authorization 헤더 설정
        response.setHeader("Authorization", "Bearer " + accessToken);
        
        // Refresh Token 쿠키 설정
        Cookie refreshTokenCookie = createRefreshTokenCookie(refreshToken);
        response.addCookie(refreshTokenCookie);
    }
    
    private Cookie createRefreshTokenCookie(String refreshToken) {
        Cookie cookie = new Cookie("refresh_token", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(REFRESH_TOKEN_MAX_AGE);
        return cookie;
    }
}
