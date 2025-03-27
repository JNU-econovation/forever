package com.example.forever.common.response;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

@UtilityClass
public class ApiResponseGenerator {

    public static ApiResponse<ApiResponse.SuccesCustomBody<Void>> success(final HttpStatus status) {
        return new ApiResponse<>(new ApiResponse.SuccesCustomBody<>(200, null, null), status);
    }

    public static <D> ApiResponse<ApiResponse.SuccesCustomBody<D>> success(final D data, final HttpStatus status) {
        return new ApiResponse<>(new ApiResponse.SuccesCustomBody<>(200, null, data), status);
    }

    public static ApiResponse<ApiResponse.FailureCustomBody> fail(final String message, final HttpStatus status) {
        return new ApiResponse<>(new ApiResponse.FailureCustomBody(status.value(), message, null), status);
    }

    public static ApiResponse<ApiResponse.FailureCustomBody> fail(
            BindingResult bindingResult, final HttpStatus status) {
        return new ApiResponse<>(
                new ApiResponse.FailureCustomBody(status.value(), createErrorMessage(bindingResult), null),
                status);
    }

    private static String createErrorMessage(BindingResult bindingResult) {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;

        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            if (!isFirst) {
                sb.append(", ");
            } else {
                isFirst = false;
            }
            sb.append("[");
            sb.append(fieldError.getField());
            sb.append("] ");
            sb.append(fieldError.getDefaultMessage());
        }

        return sb.toString();
    }
}
