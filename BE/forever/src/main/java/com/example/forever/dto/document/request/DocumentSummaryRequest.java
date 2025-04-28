package com.example.forever.dto.document.request;

import jakarta.validation.constraints.NotBlank;

public record DocumentSummaryRequest(
        @NotBlank String title,
        @NotBlank String summary,
        Long folderId

) {
}
