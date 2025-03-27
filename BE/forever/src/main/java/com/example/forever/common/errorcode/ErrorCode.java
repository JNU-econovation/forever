package com.example.forever.common.errorcode;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    String getMessage();        // 사용자에게 보일 메시지
    HttpStatus getStatus();
}
