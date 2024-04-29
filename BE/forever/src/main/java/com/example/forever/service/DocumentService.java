package com.example.forever.service;

import com.example.forever.converter.QuestionConversion;
import com.example.forever.domain.Answer;
import com.example.forever.domain.Question;
import com.example.forever.dto.*;
import com.example.forever.repository.AnswerRepository;
import com.example.forever.repository.DocumentRepository;
import com.example.forever.domain.Document;
import com.example.forever.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    public DocumentSummaryResponse saveDocumentSummary(DocumentSummaryRequest document) {
        Document savedDocument = documentRepository.save(Document.builder()
                .title(document.getTitle())
                .summary(document.getSummary())
                .build());

        Long id = savedDocument.getId();
        return new DocumentSummaryResponse(id);
    }

    public void saveDocumentQuestionAndAnswer(Long documentId, SaveQuestionAnswerRequest request) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new IllegalArgumentException("Document not found " + documentId));

        Question question = questionRepository.save(Question.builder().content(request.getQuestionContent()).document(document).build());
        answerRepository.save(Answer.builder().content(request.getAnswerContent()).question(question).build());
    }

    public GetSummaryResponse getDocumentSummary(Long documentId) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new IllegalArgumentException("Document not found " + documentId));

        return new GetSummaryResponse(document.getTitle(), document.getSummary());

    }

    public QuestionListResponse getQuestionList(Long documentId) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new IllegalArgumentException("Document not found " + documentId));

        List<Question> questionList = questionRepository.findAllByDocumentId(document.getId());

        List<EachQuestionResponse> responses = questionList.stream()
                .map(QuestionConversion::convertToEachQuestionResponse)
                .toList();
        
        return new QuestionListResponse(responses);
    }

}

