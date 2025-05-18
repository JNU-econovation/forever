package com.example.forever.service;

import com.example.forever.common.annotation.MemberInfo;
import com.example.forever.domain.Document;
import com.example.forever.domain.Folder;
import com.example.forever.domain.Member;
import com.example.forever.exception.folder.FolderNotFoundException;
import com.example.forever.repository.DocumentRepository;
import com.example.forever.repository.FolderRepository;
import com.example.forever.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FolderServiceSoftDeleteTest {

    @Mock
    private FolderRepository folderRepository;

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private FolderService folderService;

    @Test
    @DisplayName("폴더 소프트 삭제 테스트 - 정상 케이스")
    void deleteFolder_ShouldSoftDeleteFolder() {
        // Given
        Long folderId = 1L;
        Long memberId = 1L;
        MemberInfo memberInfo = new MemberInfo(memberId);

        Folder folder = Folder.builder()
                .id(folderId)
                .name("Test Folder")
                .createdBy(memberId)
                .build();

        when(folderRepository.findByIdAndIsDeletedFalse(folderId)).thenReturn(Optional.of(folder));
        when(documentRepository.findByFolderAndIsDeletedFalse(folder)).thenReturn(List.of());

        // When
        folderService.deleteFolder(folderId, memberInfo);

        // Then
        assertTrue(folder.isDeleted());
        assertNotNull(folder.getDeletedAt());
        // 실제 delete 메소드가 호출되지 않아야 함
        verify(folderRepository, never()).delete(any(Folder.class));
    }

    @Test
    @DisplayName("폴더 삭제 시 폴더 내 문서도 소프트 삭제되어야 함")
    void deleteFolder_ShouldSoftDeleteContainedDocuments() {
        // Given
        Long folderId = 1L;
        Long memberId = 1L;
        MemberInfo memberInfo = new MemberInfo(memberId);

        Folder folder = Folder.builder()
                .id(folderId)
                .name("Test Folder")
                .createdBy(memberId)
                .build();

        Member member = Member.builder()
                .id(memberId)
                .email("test@example.com")
                .build();

        Document document1 = Document.builder()
                .title("Test Document 1")
                .summary("Test Summary 1")
                .member(member)
                .folder(folder)
                .build();

        Document document2 = Document.builder()
                .title("Test Document 2")
                .summary("Test Summary 2")
                .member(member)
                .folder(folder)
                .build();

        List<Document> documents = Arrays.asList(document1, document2);

        when(folderRepository.findByIdAndIsDeletedFalse(folderId)).thenReturn(Optional.of(folder));
        when(documentRepository.findByFolderAndIsDeletedFalse(folder)).thenReturn(documents);

        // When
        folderService.deleteFolder(folderId, memberInfo);

        // Then
        assertTrue(folder.isDeleted());
        assertNotNull(folder.getDeletedAt());
        
        // 모든 문서가 소프트 삭제되었는지 확인
        for (Document document : documents) {
            assertTrue(document.isDeleted());
            assertNotNull(document.getDeletedAt());
        }
        
        // 실제 delete 메소드가 호출되지 않아야 함
        verify(folderRepository, never()).delete(any(Folder.class));
        verify(documentRepository, never()).deleteAll(anyList());
    }
    
    @Test
    @DisplayName("삭제된 폴더는 조회되지 않아야 함")
    void findFolder_ShouldNotReturnDeletedFolder() {
        // Given
        Long folderId = 1L;
        
        when(folderRepository.findByIdAndIsDeletedFalse(folderId))
                .thenReturn(Optional.empty());

        // When & Then
        assertThrows(FolderNotFoundException.class, () -> {
            folderService.updateFolder(folderId, 
                    new com.example.forever.dto.FolderUpdateRequest("New Name"), 
                    new MemberInfo(1L));
        });
        
        verify(folderRepository).findByIdAndIsDeletedFalse(folderId);
    }
}
