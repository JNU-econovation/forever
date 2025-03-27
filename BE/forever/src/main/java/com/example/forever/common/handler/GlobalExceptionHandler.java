package com.example.forever.common.handler;

import com.example.forever.common.exception.BaseException;
import com.example.forever.common.response.ApiResponse;
import com.example.forever.common.errorcode.ErrorCode;
import feign.FeignException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.MethodNotAllowedException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiResponse.FailureCustomBody> handleBaseException(BaseException e) {
        ErrorCode errorCode = e.getErrorCode();

        ApiResponse.FailureCustomBody body = new ApiResponse.FailureCustomBody(
                errorCode.getStatus().value(),
                errorCode.getMessage(),
                null
        );

        return new ResponseEntity<>(body, errorCode.getStatus());
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ApiResponse.FailureCustomBody> handleFeignError(FeignException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_GATEWAY)
                .body(new ApiResponse.FailureCustomBody(
                        e.status(),
                        "외부 API 통신 오류가 발생했습니다.",
                        null
                ));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse.FailureCustomBody> handleParamValidation(ConstraintViolationException e) {
        return ResponseEntity.badRequest().body(
                new ApiResponse.FailureCustomBody(400, "쿼리 파라미터가 유효하지 않습니다.", null)
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse.FailureCustomBody> handleDtoValidation(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().body(
                new ApiResponse.FailureCustomBody(400, "요청 필드가 유효하지 않습니다.", null)
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse.FailureCustomBody> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(
                new ApiResponse.FailureCustomBody(400, e.getMessage(), null)
        );
    }

    @ExceptionHandler(MethodNotAllowedException.class)
    public ResponseEntity<ApiResponse.FailureCustomBody> handleMethodNotAllowed(MethodNotAllowedException e) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(
                new ApiResponse.FailureCustomBody(405, "메서드가 요청 URI로 식별되는 리소스에 허용되지 않습니다.", null)
        );
    }


}