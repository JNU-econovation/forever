package com.example.forever.exception.auth;

public class AlreadyExistsEmailException extends RuntimeException {
    public AlreadyExistsEmailException(String message) {
        super(message);
    }
    
    public AlreadyExistsEmailException() {
        super("이미 존재하는 이메일입니다.");
    }
}
