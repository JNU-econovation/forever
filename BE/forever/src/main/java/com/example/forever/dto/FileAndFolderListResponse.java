package com.example.forever.dto;

import java.util.List;

public record FileAndFolderListResponse(
        List<FileResponseDto> files,
        List<FolderResponseDto> folders
) {}
