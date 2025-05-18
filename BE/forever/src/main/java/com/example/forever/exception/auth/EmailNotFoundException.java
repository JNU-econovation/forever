package com.example.forever.exception.auth;

public class EmailNotFoundException extends RuntimeException {
    public EmailNotFoundException(String message) {
        super(message);
    }
    
    public EmailNotFoundException() {
        super("해당 이메일을 찾을 수 없습니다.");
    }
}
