package com.example.forever.common.errorcode;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum FolderErrorCode implements ErrorCode {

    FOLDER_NOT_FOUND("존재하지 않는 folderId입니다.", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus status;

    @Override public String getMessage() { return message; }
    @Override public HttpStatus getStatus() { return status; }
}
