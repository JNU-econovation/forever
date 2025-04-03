package com.example.forever.dto;

public record FileResponseDto(
        Long fileId,
        String title,
        int order,
        Long folderId
) {}
