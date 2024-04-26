package com.example.forever.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

        private String status;
        private String code;
        private String message;
    }

    @Getter
    @AllArgsConstructor
    public static class SuccesCustomBody<D> implements Serializable {
        private Boolean status;
        private String message;
        private D data;
    }
}