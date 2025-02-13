package com.example.forever.controller;

import com.example.forever.dto.*;
import com.example.forever.service.DocumentService;
import com.example.forever.common.ApiResponse;
import com.example.forever.common.ApiResponseGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/documents")
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping("/summary")
    public ApiResponse<ApiResponse.SuccesCustomBody<DocumentSummaryResponse>> saveDocumentSummary(@RequestBody DocumentSummaryRequest request) {
        DocumentSummaryResponse response = documentService.saveDocumentSummary(request);
        return ApiResponseGenerator.success(response, HttpStatus.OK);
    }

    @PostMapping("/{documentId}/save")
    public ApiResponse<ApiResponse.SuccesCustomBody<Void>> saveDocumentQuestionAndAnswer(@PathVariable("documentId") Long documentId, @RequestBody SaveQuestionAnswerRequest request){
        documentService.saveDocumentQuestionAndAnswer(documentId, request);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

    @GetMapping("/{documentId}/summary")
    public ApiResponse<ApiResponse.SuccesCustomBody<GetSummaryResponse>> getDocumentSummary(@PathVariable("documentId") Long documentId){
        GetSummaryResponse response = documentService.getDocumentSummary(documentId);
        return ApiResponseGenerator.success(response, HttpStatus.OK);
    }


    @GetMapping("/{documentId}/questions")
    public ApiResponse<ApiResponse.SuccesCustomBody<QuestionListResponse>> getQuestionList(@PathVariable("documentId") Long documentId){
        QuestionListResponse response = documentService.getQuestionList(documentId);
        return ApiResponseGenerator.success(response, HttpStatus.OK);
    }


    @GetMapping("/questions/{questionId}")
    public ApiResponse<ApiResponse.SuccesCustomBody<QuestionAnswerResponse>> getQuestionAndAnswer(@PathVariable("questionId") Long questionId){
        QuestionAnswerResponse response = documentService.getQuestionAndAnswer(questionId);
        return ApiResponseGenerator.success(response, HttpStatus.OK);
    }

    @GetMapping
    public ApiResponse<ApiResponse.SuccesCustomBody<DocumentListResponse>> getDocumentList(@RequestParam(value = "page", defaultValue = "0") Long pageId){
        DocumentListResponse response = documentService.getDocumentList(pageId);
        return ApiResponseGenerator.success(response, HttpStatus.OK);
    }


}
