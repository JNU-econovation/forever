package com.example.forever.service;

import com.example.forever.common.annotation.MemberInfo;
import com.example.forever.domain.Document;
import com.example.forever.domain.Folder;
import com.example.forever.domain.Member;
import com.example.forever.exception.auth.UnauthorizedAccessException;
import com.example.forever.exception.document.DocumentNotFoundException;
import com.example.forever.exception.folder.FolderNotFoundException;
import com.example.forever.repository.DocumentRepository;
import com.example.forever.repository.FolderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * 이 테스트는 복원 기능에 대한 테스트입니다.
 * RestoreService 클래스는 아직 구현되지 않았기 때문에,
 * 이 테스트 코드는 해당 기능을 구현할 때 참고할 수 있습니다.
 */
@ExtendWith(MockitoExtension.class)
public class RestoreServiceTest {

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private FolderRepository folderRepository;

    @Mock
    private com.example.forever.common.validator.MemberValidator memberValidator;

    // 아래의 서비스는 실제로는 구현되어 있지 않습니다.
    // 이 테스트는 해당 기능을 구현할 때 가이드로 사용할 수 있습니다.
    @InjectMocks
    private RestoreService restoreService;

    private Member testMember;
    private Document testDocument;
    private Folder testFolder;
    private MemberInfo memberInfo;

    @BeforeEach
    void setUp() {
        testMember = Member.builder()
                .id(1L)
                .email("test@example.com")
                .build();

        testFolder = Folder.builder()
                .id(1L)
                .name("Test Folder")
                .createdBy(testMember.getId())
                .build();
        testFolder.delete(); // 소프트 삭제 상태로 만들기

        testDocument = Document.builder()
                .title("Test Document")
                .summary("Test Summary")
                .member(testMember)
                .folder(testFolder)
                .build();
        testDocument.delete(); // 소프트 삭제 상태로 만들기

        memberInfo = new MemberInfo(testMember.getId());
    }

    @Test
    @DisplayName("문서 복원 테스트 - 정상 케이스")
    void restoreDocument_Success() {
        // Given
        Long documentId = testDocument.getId();
        when(documentRepository.findById(documentId)).thenReturn(Optional.of(testDocument));
        when(memberValidator.validateAndGetById(testMember.getId())).thenReturn(testMember);

        // When
        restoreService.restoreDocument(documentId, memberInfo);

        // Then
        assertFalse(testDocument.isDeleted());
        assertNull(testDocument.getDeletedAt());
        verify(documentRepository).save(testDocument);
    }

    @Test
    @DisplayName("문서 복원 테스트 - 문서 없음")
    void restoreDocument_NotFound() {
        // Given
        Long documentId = 999L;
        when(documentRepository.findById(documentId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(DocumentNotFoundException.class, () -> {
            restoreService.restoreDocument(documentId, memberInfo);
        });

        verify(documentRepository, never()).save(any(Document.class));
    }

    @Test
    @DisplayName("문서 복원 테스트 - 권한 없음")
    void restoreDocument_Unauthorized() {
        // Given
        Long documentId = testDocument.getId();
        MemberInfo unauthorizedMemberInfo = new MemberInfo(2L);
        Member unauthorizedMember = Member.builder().id(2L).build();

        when(documentRepository.findById(documentId)).thenReturn(Optional.of(testDocument));
        when(memberValidator.validateAndGetById(2L)).thenReturn(unauthorizedMember);

        // When & Then
        assertThrows(UnauthorizedAccessException.class, () -> {
            restoreService.restoreDocument(documentId, unauthorizedMemberInfo);
        });

        verify(documentRepository, never()).save(any(Document.class));
    }

    @Test
    @DisplayName("폴더 복원 테스트 - 정상 케이스")
    void restoreFolder_Success() {
        // Given
        Long folderId = testFolder.getId();
        when(folderRepository.findById(folderId)).thenReturn(Optional.of(testFolder));
        when(documentRepository.findByFolder(testFolder)).thenReturn(Arrays.asList(testDocument));

        // When
        restoreService.restoreFolder(folderId, memberInfo);

        // Then
        assertFalse(testFolder.isDeleted());
        assertNull(testFolder.getDeletedAt());
        assertFalse(testDocument.isDeleted());
        assertNull(testDocument.getDeletedAt());
        verify(folderRepository).save(testFolder);
        verify(documentRepository).saveAll(anyList());
    }

    @Test
    @DisplayName("폴더 복원 테스트 - 폴더 없음")
    void restoreFolder_NotFound() {
        // Given
        Long folderId = 999L;
        when(folderRepository.findById(folderId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(FolderNotFoundException.class, () -> {
            restoreService.restoreFolder(folderId, memberInfo);
        });

        verify(folderRepository, never()).save(any(Folder.class));
        verify(documentRepository, never()).saveAll(anyList());
    }

    @Test
    @DisplayName("폴더 복원 테스트 - 권한 없음")
    void restoreFolder_Unauthorized() {
        // Given
        Long folderId = testFolder.getId();
        MemberInfo unauthorizedMemberInfo = new MemberInfo(2L);

        when(folderRepository.findById(folderId)).thenReturn(Optional.of(testFolder));

        // When & Then
        assertThrows(UnauthorizedAccessException.class, () -> {
            restoreService.restoreFolder(folderId, unauthorizedMemberInfo);
        });

        verify(folderRepository, never()).save(any(Folder.class));
        verify(documentRepository, never()).saveAll(anyList());
    }

    @Test
    @DisplayName("삭제된 문서 목록 조회 테스트")
    void getDeletedDocuments_Success() {
        // Given
        when(documentRepository.findByMemberIdAndIsDeletedTrue(testMember.getId()))
                .thenReturn(Collections.singletonList(testDocument));

        // When
        List<Document> deletedDocuments = restoreService.getDeletedDocuments(memberInfo);

        // Then
        assertEquals(1, deletedDocuments.size());
        assertEquals(testDocument.getId(), deletedDocuments.get(0).getId());
    }

    @Test
    @DisplayName("삭제된 폴더 목록 조회 테스트")
    void getDeletedFolders_Success() {
        // Given
        when(folderRepository.findByCreatedByAndIsDeletedTrue(testMember.getId()))
                .thenReturn(Collections.singletonList(testFolder));

        // When
        List<Folder> deletedFolders = restoreService.getDeletedFolders(memberInfo);

        // Then
        assertEquals(1, deletedFolders.size());
        assertEquals(testFolder.getId(), deletedFolders.get(0).getId());
    }
}
