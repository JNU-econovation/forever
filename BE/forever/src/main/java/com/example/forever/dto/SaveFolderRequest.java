package com.example.forever.dto;

import jakarta.validation.constraints.NotBlank;

public record SaveFolderRequest(
        @NotBlank String folderName
) {
}
