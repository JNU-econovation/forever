package com.example.forever.common.errorcode;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {

    ONBOARDING_REQUIRED("회원가입이 필요한 사용자입니다.", HttpStatus.UNAUTHORIZED),
    INVALID_KAKAO_CODE("유효하지 않은 인가코드입니다.", HttpStatus.UNAUTHORIZED),
    //TODO : 예외코드 고민해보기 401? 400?
    MEMBER_NOT_FOUND("존재하지 않는 회원입니다.", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED_DOCUMENT_ACCESS("문서에 대한 권한이 없습니다.", HttpStatus.FORBIDDEN),
    UNAUTHORIZED_ACCESS("접근 권한이 없습니다.", HttpStatus.UNAUTHORIZED),
    //TODO : 예외코드 고민해보기 401? 400?
    NO_AVAILABLE_USAGE("사용 가능한 횟수가 없습니다.", HttpStatus.BAD_REQUEST),
    NOT_RESOURCE_OWNER("소유자가 아닙니다.", HttpStatus.FORBIDDEN),
    INVALID_VERIFICATION_CODE("유효하지 않은 인증코드입니다.", HttpStatus.BAD_REQUEST),
    INVALID_REFRESH_TOKEN("유효하지 않은 리프레시토큰 입니다.", HttpStatus.BAD_REQUEST),
    DELETE_MEMBER("탈퇴한 회원입니다.", HttpStatus.BAD_REQUEST),
    ALREADY_EXISTS_EMAIL("이미 존재하는 이메일입니다.", HttpStatus.BAD_REQUEST);


    private final String message;
    private final HttpStatus status;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }
}