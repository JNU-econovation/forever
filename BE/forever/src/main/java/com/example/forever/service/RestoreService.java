package com.example.forever.service;

import com.example.forever.common.annotation.MemberInfo;
import com.example.forever.common.validator.MemberValidator;
import com.example.forever.domain.Document;
import com.example.forever.domain.Folder;
import com.example.forever.domain.Member;
import com.example.forever.exception.auth.UnauthorizedAccessException;
import com.example.forever.exception.document.DocumentNotFoundException;
import com.example.forever.exception.folder.FolderNotFoundException;
import com.example.forever.repository.DocumentRepository;
import com.example.forever.repository.FolderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 삭제된 문서와 폴더를 복원하는 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class RestoreService {

    private final DocumentRepository documentRepository;
    private final FolderRepository folderRepository;
    private final MemberValidator memberValidator;

    /**
     * 삭제된 문서 복원
     */
    @Transactional
    public void restoreDocument(Long documentId, MemberInfo memberInfo) {
        // 1. 문서 조회 (삭제된 문서도 조회 가능해야 함)
        Document document = documentRepository.findById(documentId)
                .orElseThrow(DocumentNotFoundException::new);

        // 2. 권한 확인
        Member member = memberValidator.validateAndGetById(memberInfo.getMemberId());
        if (!document.getMember().getId().equals(member.getId())) {
            throw new UnauthorizedAccessException();
        }

        // 3. 문서 복원
        document.restore();
        documentRepository.save(document);
    }

    /**
     * 삭제된 폴더 복원
     */
    @Transactional
    public void restoreFolder(Long folderId, MemberInfo memberInfo) {
        // 1. 폴더 조회 (삭제된 폴더도 조회 가능해야 함)
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(FolderNotFoundException::new);

        // 2. 권한 확인
        if (!folder.isOwnedBy(memberInfo.getMemberId())) {
            throw new UnauthorizedAccessException();
        }

        // 3. 폴더에 속한 모든 문서 조회 (삭제된 문서 포함)
        List<Document> documents = documentRepository.findByFolder(folder);

        // 4. 폴더 복원
        folder.restore();
        folderRepository.save(folder);

        // 5. 문서 복원
        for (Document document : documents) {
            document.restore();
        }
        documentRepository.saveAll(documents);
    }

    /**
     * 현재 사용자의 삭제된 문서 목록 조회
     */
    @Transactional(readOnly = true)
    public List<Document> getDeletedDocuments(MemberInfo memberInfo) {
        // 멤버 검증
        memberValidator.validateExistence(memberInfo.getMemberId());
        
        // 삭제된 문서만 조회
        return documentRepository.findByMemberIdAndIsDeletedTrue(memberInfo.getMemberId());
    }

    /**
     * 현재 사용자의 삭제된 폴더 목록 조회
     */
    @Transactional(readOnly = true)
    public List<Folder> getDeletedFolders(MemberInfo memberInfo) {
        // 멤버 검증
        memberValidator.validateExistence(memberInfo.getMemberId());
        
        // 삭제된 폴더만 조회
        return folderRepository.findByCreatedByAndIsDeletedTrue(memberInfo.getMemberId());
    }
}
