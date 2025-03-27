package com.example.forever.common.exception;

import com.example.forever.common.errorcode.ErrorCode;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    private final ErrorCode errorCode;

    BaseException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public static BaseException of(ErrorCode errorCode) {
        return new BaseException(errorCode);
    }
}
