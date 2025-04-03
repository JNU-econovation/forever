package com.example.forever.dto;

public record FolderResponseDto(
        Long folderId,
        String title,
        int order
) {}
