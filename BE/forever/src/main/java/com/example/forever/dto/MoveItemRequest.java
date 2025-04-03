package com.example.forever.dto;

public record MoveItemRequest(
        Long fileId,           // 이동시킬 대상의 ID (파일 or 폴더의 ID)
        boolean isFolder,      // 대상이 폴더인지 여부 (true면 폴더, false면 파일)
        Long parentFolderId,   // 이동 대상 폴더 ID (루트면 null 또는 0)
        int order,          // 새로 이동할 위치의 정렬값 (앞뒤 중간값)
        boolean isReallocate   // 최상단/최하단 등으로 이동 시 order 재정렬 필요 여부
) {}