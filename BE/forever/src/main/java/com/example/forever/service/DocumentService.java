package com.example.forever.service;

import com.example.forever.repository.DocumentRepository;
import com.example.forever.domain.Document;
import com.example.forever.dto.DocumentSummaryRequest;
import com.example.forever.dto.DocumentSummaryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DocumentService {

        private final DocumentRepository documentRepository;

        public DocumentSummaryResponse saveDocumentSummary(DocumentSummaryRequest document) {

            Document savedDocument = documentRepository.save(Document.builder()
                    .title(document.getTitle())
                    .summary(document.getContent())
                    .build());

            Long id = savedDocument.getId();
            return new DocumentSummaryResponse(id);
        }

}
