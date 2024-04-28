package com.example.forever.controller;

import com.example.forever.dto.SaveQuestionAnswerRequest;
import com.example.forever.service.DocumentService;
import com.example.forever.common.ApiResponse;
import com.example.forever.common.ApiResponseGenerator;
import com.example.forever.dto.DocumentSummaryRequest;
import com.example.forever.dto.DocumentSummaryResponse;
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

    @PostMapping("/{documentId}/questions")
    public ApiResponse<ApiResponse.SuccesCustomBody<Void>> saveDocumentQuestionAndAnswer(@PathVariable("documentId") Long documentId, @RequestBody SaveQuestionAnswerRequest request){
        documentService.saveDocumentQuestionAndAnswer(documentId, request);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }
}
