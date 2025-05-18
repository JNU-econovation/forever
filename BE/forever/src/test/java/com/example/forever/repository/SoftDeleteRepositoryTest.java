package com.example.forever.repository;

import com.example.forever.domain.Document;
import com.example.forever.domain.Folder;
import com.example.forever.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class SoftDeleteRepositoryTest {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private FolderRepository folderRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Member testMember;
    private Folder testFolder;
    private Document testDocument1, testDocument2;

    @BeforeEach
    void setUp() {
        // 테스트 멤버 생성
        testMember = Member.builder()
                .email("test@example.com")
                .nickname("테스트 사용자")
                .build();
        testMember = memberRepository.save(testMember);

        // 테스트 폴더 생성
        testFolder = Folder.builder()
                .name("테스트 폴더")
                .createdBy(testMember.getId())
                .build();
        testFolder = folderRepository.save(testFolder);

        // 테스트 문서 두 개 생성
        testDocument1 = Document.builder()
                .title("테스트 문서 1")
                .summary("테스트 요약 1")
                .member(testMember)
                .folder(testFolder)
                .build();
        testDocument1 = documentRepository.save(testDocument1);

        testDocument2 = Document.builder()
                .title("테스트 문서 2")
                .summary("테스트 요약 2")
                .member(testMember)
                .folder(testFolder)
                .build();
        testDocument2 = documentRepository.save(testDocument2);
    }

    @Test
    @DisplayName("findByIdAndIsDeletedFalse 메소드 테스트 - 삭제되지 않은 문서")
    void findByIdAndIsDeletedFalse_NotDeletedDocument() {
        // Given
        Long documentId = testDocument1.getId();
        
        // When
        Optional<Document> foundDocument = documentRepository.findByIdAndIsDeletedFalse(documentId);
        
        // Then
        assertTrue(foundDocument.isPresent());
        assertEquals(documentId, foundDocument.get().getId());
    }

    @Test
    @DisplayName("findByIdAndIsDeletedFalse 메소드 테스트 - 삭제된 문서")
    void findByIdAndIsDeletedFalse_DeletedDocument() {
        // Given
        Long documentId = testDocument1.getId();
        testDocument1.delete();
        documentRepository.save(testDocument1);
        
        // When
        Optional<Document> foundDocument = documentRepository.findByIdAndIsDeletedFalse(documentId);
        
        // Then
        assertTrue(foundDocument.isEmpty());
    }

    @Test
    @DisplayName("findByFolderAndIsDeletedFalse 메소드 테스트 - 모든 문서가 삭제되지 않은 경우")
    void findByFolderAndIsDeletedFalse_AllNotDeleted() {
        // Given
        
        // When
        List<Document> documents = documentRepository.findByFolderAndIsDeletedFalse(testFolder);
        
        // Then
        assertEquals(2, documents.size());
    }

    @Test
    @DisplayName("findByFolderAndIsDeletedFalse 메소드 테스트 - 일부 문서가 삭제된 경우")
    void findByFolderAndIsDeletedFalse_SomeDeleted() {
        // Given
        testDocument1.delete();
        documentRepository.save(testDocument1);
        
        // When
        List<Document> documents = documentRepository.findByFolderAndIsDeletedFalse(testFolder);
        
        // Then
        assertEquals(1, documents.size());
        assertEquals(testDocument2.getId(), documents.get(0).getId());
    }

    @Test
    @DisplayName("findByFolderAndIsDeletedFalse 메소드 테스트 - 모든 문서가 삭제된 경우")
    void findByFolderAndIsDeletedFalse_AllDeleted() {
        // Given
        testDocument1.delete();
        testDocument2.delete();
        documentRepository.saveAll(List.of(testDocument1, testDocument2));
        
        // When
        List<Document> documents = documentRepository.findByFolderAndIsDeletedFalse(testFolder);
        
        // Then
        assertTrue(documents.isEmpty());
    }

    @Test
    @DisplayName("findByFolder 메소드 테스트 - 소프트 삭제된 문서도 포함되어야 함")
    void findByFolder_IncludesDeletedDocuments() {
        // Given
        testDocument1.delete();
        documentRepository.save(testDocument1);
        
        // When
        List<Document> documents = documentRepository.findByFolder(testFolder);
        
        // Then
        assertEquals(2, documents.size()); // 소프트 삭제된 문서도 포함되어야 함
    }

    @Test
    @DisplayName("findByIdAndIsDeletedFalse 메소드 테스트 - 삭제되지 않은 폴더")
    void findByIdAndIsDeletedFalse_NotDeletedFolder() {
        // Given
        Long folderId = testFolder.getId();
        
        // When
        Optional<Folder> foundFolder = folderRepository.findByIdAndIsDeletedFalse(folderId);
        
        // Then
        assertTrue(foundFolder.isPresent());
        assertEquals(folderId, foundFolder.get().getId());
    }

    @Test
    @DisplayName("findByIdAndIsDeletedFalse 메소드 테스트 - 삭제된 폴더")
    void findByIdAndIsDeletedFalse_DeletedFolder() {
        // Given
        Long folderId = testFolder.getId();
        testFolder.delete();
        folderRepository.save(testFolder);
        
        // When
        Optional<Folder> foundFolder = folderRepository.findByIdAndIsDeletedFalse(folderId);
        
        // Then
        assertTrue(foundFolder.isEmpty());
    }
}
