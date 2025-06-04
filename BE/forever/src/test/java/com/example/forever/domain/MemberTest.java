package com.example.forever.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {
    
    @Test
    @DisplayName("Member 엔티티 생성 성공 테스트")
    void createMember_Success() {
        // Given
        String email = "test@example.com";
        String nickname = "테스터";
        
        // When
        Member member = Member.builder()
                .email(email)
                .nickname(nickname)
                .build();
        
        // Then
        assertNotNull(member);
        assertEquals(email, member.getEmail());
        assertEquals(nickname, member.getNickname());
        assertEquals(3, member.getAvailableTokens()); // 기본값 3으로 변경
        assertFalse(member.isDeleted()); // 기본값 확인
    }
    
    @Test
    @DisplayName("Member 토큰 사용 테스트")
    void useToken_Success() {
        // Given
        Member member = Member.builder()
                .email("test@example.com")
                .availableTokens(5)
                .build();
        
        // When
        member.useToken();
        
        // Then
        assertEquals(4, member.getAvailableTokens());
    }
    
    @Test
    @DisplayName("Member 토큰 사용 가능 여부 테스트 - 토큰이 있는 경우")
    void isAvailableTokens_WithTokens_ReturnsTrue() {
        // Given
        Member member = Member.builder()
                .email("test@example.com")
                .availableTokens(1)
                .build();
        
        // When
        boolean isAvailable = member.isAvailableTokens();
        
        // Then
        assertTrue(isAvailable);
    }
    
    @Test
    @DisplayName("Member 토큰 사용 가능 여부 테스트 - 토큰이 없는 경우")
    void isAvailableTokens_WithoutTokens_ReturnsFalse() {
        // Given
        Member member = Member.builder()
                .email("test@example.com")
                .availableTokens(0)
                .build();
        
        // When
        boolean isAvailable = member.isAvailableTokens();
        
        // Then
        assertFalse(isAvailable);
    }
    
    @Test
    @DisplayName("Member 소프트 삭제 테스트")
    void deleteMember_Success() {
        // Given
        Member member = Member.builder()
                .email("test@example.com")
                .build();
        
        // When
        member.delete();
        
        // Then
        assertTrue(member.isDeleted());
        assertNotNull(member.getDeletedAt());
        assertTrue(LocalDateTime.now().isAfter(member.getDeletedAt()) || 
                LocalDateTime.now().isEqual(member.getDeletedAt()));
    }
    
    @Test
    @DisplayName("Member 리프레시 토큰 업데이트 테스트")
    void updateRefreshToken_Success() {
        // Given
        Member member = Member.builder()
                .email("test@example.com")
                .refreshToken("old-token")
                .build();
        
        String newToken = "new-refresh-token";
        
        // When
        member.updateRefreshToken(newToken);
        
        // Then
        assertEquals(newToken, member.getRefreshToken());
    }
    
    @Test
    @DisplayName("Member 카카오 액세스 토큰 업데이트 테스트")
    void updateKakaoAccessToken_Success() {
        // Given
        Member member = Member.builder()
                .email("test@example.com")
                .kakaoAccessToken("old-kakao-token")
                .build();
        
        String newToken = "new-kakao-token";
        
        // When
        member.updateKakaoAccessToken(newToken);
        
        // Then
        assertEquals(newToken, member.getKakaoAccessToken());
    }
    
    @Test
    @DisplayName("Member 정책 동의 테스트")
    void agreePolicy_Success() {
        // Given
        Member member = Member.builder()
                .email("test@example.com")
                .isAgreedPolicy(false)
                .build();
        
        // When
        member.agreePolicy();
        
        // Then
        assertTrue(member.getIsAgreedPolicy());
    }
    
    @Test
    @DisplayName("Member 약관 동의 테스트")
    void agreeTerms_Success() {
        // Given
        Member member = Member.builder()
                .email("test@example.com")
                .isAgreedTerms(false)
                .build();
        
        // When
        member.agreeTerms();
        
        // Then
        assertTrue(member.getIsAgreedTerms());
    }
}
