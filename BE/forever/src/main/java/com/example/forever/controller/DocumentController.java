package com.example.forever.controller;

import com.example.forever.common.annotation.AuthMember;
import com.example.forever.common.annotation.MemberInfo;
import com.example.forever.dto.FileAndFolderListResponse;
import com.example.forever.dto.SaveFolderRequest;
import com.example.forever.dto.document.request.DocumentSummaryRequest;
import com.example.forever.dto.document.request.DocumentUpdateRequest;
import com.example.forever.dto.document.request.SaveQuestionAnswerRequest;
import com.example.forever.dto.document.response.DocumentListResponse;
import com.example.forever.dto.document.response.DocumentSummaryResponse;
import com.example.forever.dto.document.response.GetSummaryResponse;
import com.example.forever.dto.document.response.QuestionAnswerResponse;
import com.example.forever.dto.document.response.QuestionListResponse;
import com.example.forever.service.DocumentService;
import com.example.forever.common.response.ApiResponse;
import com.example.forever.common.response.ApiResponseGenerator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/documents")
public class DocumentController {

    private final DocumentService documentService;

//
//
//    @GetMapping
//    public ApiResponse<ApiResponse.SuccesCustomBody<DocumentListResponse>> getDocumentList(@RequestParam(value = "page", defaultValue = "0") Long pageId, @AuthMember MemberInfo memberInfo){
//        DocumentListResponse response = documentService.getDocumentList(pageId,memberInfo);
//        return ApiResponseGenerator.success(response, HttpStatus.OK);
//    }

    //폴더 생성
    @PostMapping("/folder")
    public ApiResponse<ApiResponse.SuccesCustomBody<Void>> createFolder(@Valid @RequestBody SaveFolderRequest request, @AuthMember MemberInfo memberInfo){
        documentService.createFolder(request,memberInfo);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

    /**
     * 파일 수정 삭제
     * @param documentId
     * @param request
     * @param memberInfo
     * @return
     */
    @PutMapping("/{documentId}")
    public ApiResponse<ApiResponse.SuccesCustomBody<Void>> updateDocument(@PathVariable("documentId") Long documentId, @Valid @RequestBody DocumentUpdateRequest request, @AuthMember MemberInfo memberInfo){
        documentService.updateDocument(documentId, request,memberInfo);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

    @DeleteMapping("/{documentId}")
    public ApiResponse<ApiResponse.SuccesCustomBody<Void>> deleteDocument(@PathVariable("documentId") Long documentId, @AuthMember MemberInfo memberInfo){
        documentService.deleteDocument(documentId,memberInfo);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }


    @PostMapping("/summary")
    public ApiResponse<ApiResponse.SuccesCustomBody<DocumentSummaryResponse>> saveDocumentSummary(@Valid @RequestBody DocumentSummaryRequest request, @AuthMember MemberInfo memberInfo) {
        DocumentSummaryResponse response = documentService.saveDocumentSummary(request,memberInfo);
        return ApiResponseGenerator.success(response, HttpStatus.OK);
    }

    @PostMapping("/{documentId}/save")
    public ApiResponse<ApiResponse.SuccesCustomBody<Void>> saveDocumentQuestionAndAnswer(@PathVariable("documentId") Long documentId, @Valid @RequestBody SaveQuestionAnswerRequest request, @AuthMember MemberInfo memberInfo){
        documentService.saveDocumentQuestionAndAnswer(documentId, request,memberInfo);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

    @GetMapping("/{documentId}/summary")
    public ApiResponse<ApiResponse.SuccesCustomBody<GetSummaryResponse>> getDocumentSummary(@PathVariable("documentId") Long documentId, @AuthMember MemberInfo memberInfo){
        GetSummaryResponse response = documentService.getDocumentSummary(documentId,memberInfo);
        return ApiResponseGenerator.success(response, HttpStatus.OK);
    }


    @GetMapping("/{documentId}/questions")
    public ApiResponse<ApiResponse.SuccesCustomBody<QuestionListResponse>> getQuestionList(@PathVariable("documentId") Long documentId, @AuthMember MemberInfo memberInfo){
        QuestionListResponse response = documentService.getQuestionList(documentId,memberInfo);
        return ApiResponseGenerator.success(response, HttpStatus.OK);
    }


    @GetMapping("/questions/{questionId}")
    public ApiResponse<ApiResponse.SuccesCustomBody<QuestionAnswerResponse>> getQuestionAndAnswer(@PathVariable("questionId") Long questionId, @AuthMember MemberInfo memberInfo){
        QuestionAnswerResponse response = documentService.getQuestionAndAnswer(questionId,memberInfo);
        return ApiResponseGenerator.success(response, HttpStatus.OK);
    }

}
