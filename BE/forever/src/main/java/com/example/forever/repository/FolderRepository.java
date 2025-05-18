package com.example.forever.repository;

import com.example.forever.domain.Document;
import com.example.forever.domain.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FolderRepository extends JpaRepository<Folder, Long> {
    // Soft Delete를 고려한 조회 메소드
    List<Folder> findByCreatedByAndIsDeletedFalse(Long createdBy);
    List<Folder> findByCreatedByAndIsDeletedTrue(Long createdBy);
    
    // 삭제되지 않은 폴더만 ID로 조회
    Optional<Folder> findByIdAndIsDeletedFalse(Long id);
}

