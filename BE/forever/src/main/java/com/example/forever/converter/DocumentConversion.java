package com.example.forever.converter;

import com.example.forever.domain.Document;
import com.example.forever.dto.document.response.EachDocumentResponse;

public class DocumentConversion {
    public static EachDocumentResponse convertToEachDocumentResponse(Document document) {
        return new EachDocumentResponse(
                document.getId(),
                document.getTitle()
        );
    }
}
