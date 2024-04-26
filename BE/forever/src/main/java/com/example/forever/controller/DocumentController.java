package com.example.forever.controller;

import com.example.forever.service.DocumentService;
import com.example.forever.common.ApiResponse;
import com.example.forever.common.ApiResponseGenerator;
import com.example.forever.common.ErrorCode;
import com.example.forever.dto.DocumentSummaryRequest;
import com.example.forever.dto.DocumentSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping("/documents/summary")
    public ApiResponse<ApiResponse.SuccesCustomBody<DocumentSummaryResponse>> saveDocumentSummary(@RequestBody DocumentSummaryRequest request){
        DocumentSummaryResponse response = documentService.saveDocumentSummary(request);
        return ApiResponseGenerator.success(response, HttpStatus.OK);
    }
}
