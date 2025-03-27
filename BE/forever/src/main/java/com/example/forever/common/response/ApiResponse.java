package com.example.forever.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;

import org.springframework.util.MultiValueMap;

@Getter
public class ApiResponse<B> extends ResponseEntity<B> {

    public ApiResponse(final HttpStatus status) {
        super(status);
    }

    public ApiResponse(final B body, final HttpStatus status) {
        super(body, status);
    }

    public ApiResponse(final B body, MultiValueMap<String, String> headers, HttpStatus status) {
        super(body, headers, status);
    }

    @Getter
    @AllArgsConstructor
    public static class FailureCustomBody implements Serializable {

        private int status;
        private String message;
        private String data;
    }

    @Getter
    @AllArgsConstructor
    public static class SuccesCustomBody<D> implements Serializable {
        private int status;
        private String message;
        private D data;
    }
}