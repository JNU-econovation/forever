package com.example.forever.dto.document.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DocumentListResponse {
    List<EachDocumentResponse> documents;
}
