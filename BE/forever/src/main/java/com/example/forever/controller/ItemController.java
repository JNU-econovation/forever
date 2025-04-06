package com.example.forever.controller;

import com.example.forever.common.annotation.AuthMember;
import com.example.forever.common.annotation.MemberInfo;
import com.example.forever.common.response.ApiResponse;
import com.example.forever.common.response.ApiResponseGenerator;
import com.example.forever.dto.FileAndFolderListResponse;
import com.example.forever.dto.MoveItemRequest;
import com.example.forever.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/documents")
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public ApiResponse<ApiResponse.SuccesCustomBody<FileAndFolderListResponse>> getDocumentListNew(@AuthMember MemberInfo memberInfo){
        FileAndFolderListResponse response = itemService.getDocumentListNew(memberInfo);
        return ApiResponseGenerator.success(response, HttpStatus.OK);
    }


    //파일위치 이동
    @PostMapping("/move")
    public ApiResponse<ApiResponse.SuccesCustomBody<Void>> getDocumentListNew(@RequestBody MoveItemRequest request, @AuthMember MemberInfo memberInfo){
        itemService.moveItem(request, memberInfo);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }
}
