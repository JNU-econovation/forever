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

    public QuestionAnswerResponse getQuestionAndAnswer(Long questionId) {
      //  Question question = questionRepository.findById(questionId)
       //         .orElseThrow(() -> new IllegalArgumentException("Question not found " + questionId));

        Answer answer = answerRepository.findByQuestionId(questionId).orElseThrow(
                () -> new IllegalArgumentException("Answer not found " + questionId)
        );

        Question question = answer.getQuestion();

        return new QuestionAnswerResponse(question.getContent(), answer.getContent());
    }
    //여기서 조회를 2번해서 가져올 수 있지만 서로 연관되어있는 question, answer를 한번에 가져오기 위해 join fetch를 사용하여 조회를 한번만 하도록
    //하지만 이렇게 하면 questionId의 값이 없을 경우에는 예외가 발생하게 되므로 Optional로 감싸서 반환하도록

}

