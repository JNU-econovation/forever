package com.example.forever.service;

import com.example.forever.common.annotation.MemberInfo;
import com.example.forever.common.validator.MemberValidator;
import com.example.forever.domain.Document;
import com.example.forever.domain.Folder;
import com.example.forever.domain.Item;
import com.example.forever.domain.ItemType;
import com.example.forever.domain.Member;
import com.example.forever.dto.FileAndFolderListResponse;
import com.example.forever.dto.FileResponseDto;
import com.example.forever.dto.FolderResponseDto;
import com.example.forever.dto.MoveItemRequest;
import com.example.forever.exception.auth.NotResourceOwnerException;
import com.example.forever.exception.document.DocumentNotFoundException;
import com.example.forever.repository.DocumentRepository;
import com.example.forever.repository.FolderRepository;
import com.example.forever.repository.ItemRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;
    private final DocumentRepository documentRepository;
    private final MemberValidator memberValidator;
    private final FolderRepository folderRepository;

    @Transactional(readOnly = true)
    public FileAndFolderListResponse getDocumentListNew(MemberInfo memberInfo) {

        Member member = memberValidator.validateAndGetById(memberInfo.getMemberId());

        // 모든 Item 가져오기 (order 기준 정렬)
        List<Item> allItems = itemRepository.findAllByOwner(member.getId());

        List<FileResponseDto> fileDtos = new ArrayList<>();
        List<FolderResponseDto> folderDtos = new ArrayList<>();

        for (Item item : allItems) {
            if (item.getType() == ItemType.FILE) {
                Document file = documentRepository.findByIdAndIsDeletedFalse(item.getRefId())
                        .orElseThrow(() -> new IllegalArgumentException("File not found"));

                Long folderId = (item.getFolder() != null) ? item.getFolder().getId() : 0L;

                fileDtos.add(new FileResponseDto(
                        file.getId(),
                        file.getTitle(),
                        item.getOrderValue(),
                        folderId
                ));
            } else if (item.getType() == ItemType.FOLDER) {
                Folder folder = folderRepository.findByIdAndIsDeletedFalse(item.getRefId())
                        .orElseThrow(() -> new IllegalArgumentException("Folder not found"));

                folderDtos.add(new FolderResponseDto(
                        folder.getId(),
                        folder.getName(),
                        item.getOrderValue()
                ));
            }
        }

        return new FileAndFolderListResponse(fileDtos, folderDtos);
    }

    @Transactional
    public void moveItem(MoveItemRequest request, MemberInfo memberInfo) {
        Member member = memberValidator.validateAndGetById(memberInfo.getMemberId());

        // 1. Item 조회
        ItemType type = request.isFolder() ? ItemType.FOLDER : ItemType.FILE;

        Item item = itemRepository.findByTypeAndRefId(type, request.fileId())
                .orElseThrow(DocumentNotFoundException::new);

        // 2. 소유자 검증
        if (type == ItemType.FILE) {
            Document document = documentRepository.findByIdAndIsDeletedFalse(item.getRefId())
                    .orElseThrow(DocumentNotFoundException::new);

            if (!document.getMember().getId().equals(member.getId())) {
                throw new NotResourceOwnerException();
            }

        } else if (type == ItemType.FOLDER) {
            Folder folder = folderRepository.findByIdAndIsDeletedFalse(item.getRefId())
                    .orElseThrow(DocumentNotFoundException::new);

            if (!folder.getCreatedBy().equals(member.getId())) {
                throw new NotResourceOwnerException();
            }
        }

        // 2. 새 폴더 조회 (null이면 루트)
        Folder targetFolder = null;
        if (request.parentFolderId() != null && request.parentFolderId() != 0) {
            targetFolder = folderRepository.findByIdAndIsDeletedFalse(request.parentFolderId())
                    .orElseThrow(() -> new IllegalArgumentException("Target folder not found"));
        }

        // 3. 폴더/순서 이동 처리
        item.moveTo(targetFolder, request.order());

        // 4. 재정렬 처리
        if (request.isReallocate()) {
            List<Item> siblings = itemRepository.findByFolderOrderByOrderValueAsc(targetFolder);
            int order = 100;
            for (Item s : siblings) {
                s.updateOrder(order);
                order += 100;
            }
        }
    }


}
