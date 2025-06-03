package com.example.forever.common.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;
    
    @BeforeEach
    void setUp() {
        jwtTokenProvider = new JwtTokenProvider();
        // 테스트용 시크릿 키 주입
        ReflectionTestUtils.setField(jwtTokenProvider, "SECRET_KEY", "test-jwt-secret-key-for-testing-only-minimum-32-characters");
        jwtTokenProvider.init(); // PostConstruct 메소드 수동 호출
    }
    
    @Test
    @DisplayName("액세스 토큰 생성 테스트")
    void createAccessToken_Success() {
        // Given
        Long userId = 1L;
        String role = "USER";
        
        // When
        String token = jwtTokenProvider.createAccessToken(userId, role);
        
        // Then
        assertNotNull(token);
        assertTrue(token.length() > 0);
        assertTrue(jwtTokenProvider.validateToken(token));
    }
    
    @Test
    @DisplayName("리프레시 토큰 생성 테스트")
    void createRefreshToken_Success() {
        // Given
        Long userId = 1L;
        
        // When
        String token = jwtTokenProvider.createRefreshToken(userId);
        
        // Then
        assertNotNull(token);
        assertTrue(token.length() > 0);
        assertTrue(jwtTokenProvider.validateToken(token));
    }
    
    @Test
    @DisplayName("토큰 검증 테스트 - 유효한 토큰")
    void validateToken_ValidToken_ReturnsTrue() {
        // Given
        Long userId = 1L;
        String role = "USER";
        String token = jwtTokenProvider.createAccessToken(userId, role);
        
        // When
        boolean isValid = jwtTokenProvider.validateToken(token);
        
        // Then
        assertTrue(isValid);
    }
    
    @Test
    @DisplayName("토큰 검증 테스트 - 잘못된 토큰")
    void validateToken_InvalidToken_ReturnsFalse() {
        // Given
        String invalidToken = "invalid.token.string";
        
        // When
        boolean isValid = jwtTokenProvider.validateToken(invalidToken);
        
        // Then
        assertFalse(isValid);
    }
    
    @Test
    @DisplayName("토큰에서 사용자 ID 추출 테스트")
    void getMemberIdFromToken_ValidToken_ReturnsUserId() {
        // Given
        Long expectedUserId = 1L;
        String role = "USER";
        String token = jwtTokenProvider.createAccessToken(expectedUserId, role);
        
        // When
        Long extractedUserId = jwtTokenProvider.getMemberIdFromToken(token);
        
        // Then
        assertEquals(expectedUserId, extractedUserId);
    }
    
    @Test
    @DisplayName("토큰에서 역할 추출 테스트")
    void getRoleFromToken_ValidToken_ReturnsRole() {
        // Given
        Long userId = 1L;
        String expectedRole = "ADMIN";
        String token = jwtTokenProvider.createAccessToken(userId, expectedRole);
        
        // When
        String extractedRole = jwtTokenProvider.getRoleFromToken(token);
        
        // Then
        assertEquals(expectedRole, extractedRole);
    }
    
    @Test
    @DisplayName("Bearer 토큰 추출 테스트")
    void extractBearerToken_TokenWithBearer_ExtractsCorrectly() {
        // Given
        Long userId = 1L;
        String role = "USER";
        String token = jwtTokenProvider.createAccessToken(userId, role);
        String bearerToken = "Bearer " + token;
        
        // When
        // 비공개 메소드를 직접 테스트할 수 없으므로 다른 메소드를 통해 간접적으로 테스트
        boolean isValid = jwtTokenProvider.validateToken(bearerToken);
        
        // Then
        assertTrue(isValid);
    }
}
