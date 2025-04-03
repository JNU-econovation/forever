package com.example.forever.dto.document.request;

import jakarta.validation.constraints.NotBlank;

public record DocumentUpdateRequest(
        @NotBlank String newName
) {
}
