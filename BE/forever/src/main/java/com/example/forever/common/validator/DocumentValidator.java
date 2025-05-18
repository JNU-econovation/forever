package com.example.forever.common.validator;

import com.example.forever.domain.Document;
import com.example.forever.exception.document.DocumentNotFoundException;
import com.example.forever.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DocumentValidator {

    private final DocumentRepository documentRepository;

    public Document validateAndGetById(Long documentId) {
        return documentRepository.findByIdAndIsDeletedFalse(documentId)
                .orElseThrow(DocumentNotFoundException::new);
    }
}
