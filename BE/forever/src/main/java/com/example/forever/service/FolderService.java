package com.example.forever.service;


import com.example.forever.common.annotation.MemberInfo;
import com.example.forever.domain.Document;
import com.example.forever.domain.Folder;
import com.example.forever.dto.FolderUpdateRequest;
import com.example.forever.exception.auth.UnauthorizedAccessException;
import com.example.forever.exception.folder.FolderNotFoundException;
import com.example.forever.repository.DocumentRepository;
import com.example.forever.repository.FolderRepository;
import com.example.forever.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class FolderService {

    private final FolderRepository folderRepository;
    private final MemberRepository memberRepository;
    private final DocumentRepository documentRepository;

    /**
     * 폴더 이름 수정
     */
    @Transactional
    public void updateFolder(Long folderId, FolderUpdateRequest request, MemberInfo memberInfo) {
        Folder folder = folderRepository.findByIdAndIsDeletedFalse(folderId)
                .orElseThrow(FolderNotFoundException::new);

        validateFolderOwner(folder, memberInfo);

        folder.updateName(request.newName());
    }

    /**
     * 폴더 삭제 (소프트 삭제)
     */
    @Transactional
    public void deleteFolder(Long folderId, MemberInfo memberInfo) {
        Folder folder = folderRepository.findByIdAndIsDeletedFalse(folderId)
                .orElseThrow(FolderNotFoundException::new);

        validateFolderOwner(folder, memberInfo);

        // 폴더에 속한 모든 문서 조회 (삭제되지 않은 문서만)
        List<Document> documents = documentRepository.findByFolderAndIsDeletedFalse(folder);
        
        // 문서들 소프트 삭제
        if (!documents.isEmpty()) {
            documents.forEach(Document::delete);
        }

        // 폴더 소프트 삭제
        folder.delete();
    }

    private void validateFolderOwner(Folder folder, MemberInfo memberInfo) {
        if (!folder.getCreatedBy().equals(memberInfo.getMemberId())) {
            throw new UnauthorizedAccessException();
        }
    }


}
