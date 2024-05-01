package com.example.forever.converter;

import com.example.forever.domain.Document;
import com.example.forever.domain.Question;
import com.example.forever.dto.EachDocumentResponse;
import com.example.forever.dto.EachQuestionResponse;

public class DocumentConversion {
    public static EachDocumentResponse convertToEachDocumentResponse(Document document) {
        return new EachDocumentResponse(
                document.getId(),
                document.getTitle()
        );
    }
}
