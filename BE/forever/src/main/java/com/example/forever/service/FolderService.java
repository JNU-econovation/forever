package com.example.forever.service;


import com.example.forever.common.annotation.MemberInfo;
import com.example.forever.domain.Folder;
import com.example.forever.dto.FolderUpdateRequest;
import com.example.forever.exception.auth.UnauthorizedAccessException;
import com.example.forever.exception.folder.FolderNotFoundException;
import com.example.forever.repository.FolderRepository;
import com.example.forever.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class FolderService {

    private final FolderRepository folderRepository;
    private final MemberRepository memberRepository;

    /**
     * 폴더 이름 수정
     */
    @Transactional
    public void updateFolder(Long folderId, FolderUpdateRequest request, MemberInfo memberInfo) {
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(FolderNotFoundException::new);

        validateFolderOwner(folder, memberInfo);

//        // 이름 중복 검사 (동일한 부모 폴더 내에서만 중복 체크)
//        boolean isDuplicate = folderRepository.existsByNameAndParentFolderIdAndIdNot(
//                request.getName(), folder.getParentFolderId(), folderId
//        );
//
//        if (isDuplicate) {
//            throw new FolderNameAlreadyExistsException();
//        }

        folder.updateName(request.newName());
    }

    /**
     * 폴더 삭제
     */
    @Transactional
    public void deleteFolder(Long folderId, MemberInfo memberInfo) {
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(FolderNotFoundException::new);

        validateFolderOwner(folder, memberInfo);

        // 하위 아이템 정리 (Cascade 설정 시 생략 가능)
        folderRepository.delete(folder);
    }

    private void validateFolderOwner(Folder folder, MemberInfo memberInfo) {
        if (!folder.getCreatedBy().equals(memberInfo.getMemberId())) {
            throw new UnauthorizedAccessException();
        }
    }


}
