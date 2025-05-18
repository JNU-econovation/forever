package com.example.forever.repository;

import com.example.forever.domain.Document;
import com.example.forever.domain.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    // Soft Delete를 고려한 조회 메소드
    List<Document> findByFolderAndIsDeletedFalse(Folder folder);
    List<Document> findByMemberIdAndIsDeletedFalse(Long memberId);
    List<Document> findByFolder(Folder folder);
    List<Document> findByMemberIdAndIsDeletedTrue(Long memberId);
    
    // 삭제되지 않은 문서만 ID로 조회
    Optional<Document> findByIdAndIsDeletedFalse(Long id);
}
