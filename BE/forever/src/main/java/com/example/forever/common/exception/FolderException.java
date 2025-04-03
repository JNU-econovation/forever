package com.example.forever.common.exception;

import com.example.forever.common.errorcode.ErrorCode;

public abstract class FolderException extends BaseException {
    protected FolderException(ErrorCode errorCode) {
        super(errorCode);
    }
}