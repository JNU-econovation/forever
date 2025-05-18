package com.example.forever.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

public class SoftDeleteEntityTest {

    @Test
    @DisplayName("Document 엔티티 소프트 삭제 테스트")
    void document_SoftDelete() {
        // Given
        Member member = Member.builder()
                .id(1L)
                .email("test@example.com")
                .build();
        
        Document document = Document.builder()
                .title("Test Document")
                .summary("Test Summary")
                .member(member)
                .build();
        
        // 초기 상태 확인
        assertFalse(document.isDeleted());
        assertNull(document.getDeletedAt());
        
        // When
        document.delete();
        
        // Then
        assertTrue(document.isDeleted());
        assertNotNull(document.getDeletedAt());
        
        // deletedAt은 현재 시간과 크게 차이가 나지 않아야 함 (1초 이내)
        LocalDateTime now = LocalDateTime.now();
        long diff = ChronoUnit.SECONDS.between(document.getDeletedAt(), now);
        assertTrue(Math.abs(diff) < 1);
    }
    
    @Test
    @DisplayName("Folder 엔티티 소프트 삭제 테스트")
    void folder_SoftDelete() {
        // Given
        Folder folder = Folder.builder()
                .name("Test Folder")
                .createdBy(1L)
                .build();
        
        // 초기 상태 확인
        assertFalse(folder.isDeleted());
        assertNull(folder.getDeletedAt());
        
        // When
        folder.delete();
        
        // Then
        assertTrue(folder.isDeleted());
        assertNotNull(folder.getDeletedAt());
        
        // deletedAt은 현재 시간과 크게 차이가 나지 않아야 함 (1초 이내)
        LocalDateTime now = LocalDateTime.now();
        long diff = ChronoUnit.SECONDS.between(folder.getDeletedAt(), now);
        assertTrue(Math.abs(diff) < 1);
    }
    
    @Test
    @DisplayName("Document 엔티티 복원 테스트")
    void document_Restore() {
        // Given
        Member member = Member.builder()
                .id(1L)
                .email("test@example.com")
                .build();
        
        Document document = Document.builder()
                .title("Test Document")
                .summary("Test Summary")
                .member(member)
                .build();
        
        document.delete(); // 먼저 삭제
        assertTrue(document.isDeleted());
        assertNotNull(document.getDeletedAt());
        
        // When
        document.restore();
        
        // Then
        assertFalse(document.isDeleted());
        assertNull(document.getDeletedAt());
    }
    
    @Test
    @DisplayName("Folder 엔티티 복원 테스트")
    void folder_Restore() {
        // Given
        Folder folder = Folder.builder()
                .name("Test Folder")
                .createdBy(1L)
                .build();
        
        folder.delete(); // 먼저 삭제
        assertTrue(folder.isDeleted());
        assertNotNull(folder.getDeletedAt());
        
        // When
        folder.restore();
        
        // Then
        assertFalse(folder.isDeleted());
        assertNull(folder.getDeletedAt());
    }
}
