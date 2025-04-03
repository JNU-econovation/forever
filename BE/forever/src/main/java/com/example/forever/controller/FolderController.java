package com.example.forever.controller;

import com.example.forever.common.annotation.AuthMember;
import com.example.forever.common.annotation.MemberInfo;
import com.example.forever.common.response.ApiResponse;
import com.example.forever.common.response.ApiResponseGenerator;
import com.example.forever.dto.FolderUpdateRequest;
import com.example.forever.service.FolderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/folder")
public class FolderController {

    private final FolderService folderService;

    /**
     * 폴더 수정 삭제
     *
     * @param request
     * @param memberInfo
     * @return
     */

    @PutMapping("/{folderId}")
    public ApiResponse<ApiResponse.SuccesCustomBody<Void>> updateFolder(
            @PathVariable("folderId") Long folderId,
            @Valid @RequestBody FolderUpdateRequest request,
            @AuthMember MemberInfo memberInfo) {
        folderService.updateFolder(folderId, request, memberInfo);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

    /**
     * 폴더 삭제
     */
    @DeleteMapping("/{folderId}")
    public ApiResponse<ApiResponse.SuccesCustomBody<Void>> deleteFolder(
            @PathVariable("folderId") Long folderId,
            @AuthMember MemberInfo memberInfo) {
        folderService.deleteFolder(folderId, memberInfo);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

}
