package com.example.forever.common.errorcode;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum QuestionErrorCode implements ErrorCode {

    QUESTION_NOT_FOUND("존재하지 않는 questionId입니다.", HttpStatus.BAD_REQUEST),
    ANSWER_NOT_FOUND("존재하지 않는 answerId입니다.", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus status;

    @Override public String getMessage() { return message; }
    @Override public HttpStatus getStatus() { return status; }
}
