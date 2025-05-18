package com.example.forever.dto.restore;

import com.example.forever.dto.FileResponseDto;
import com.example.forever.dto.FolderResponseDto;

import java.util.List;

/**
 * 삭제된 문서와 폴더 목록 응답 DTO
 */
public record DeletedItemsResponse(
        List<FileResponseDto> deletedFiles,
        List<FolderResponseDto> deletedFolders
) {
}
