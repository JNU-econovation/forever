package com.example.forever.application.member;

public record WithdrawResult(boolean success, String message) {
    
    public static WithdrawResult createSuccess() {
        return new WithdrawResult(true, "회원탈퇴가 완료되었습니다.");
    }
    
    public static WithdrawResult createFailure(String message) {
        return new WithdrawResult(false, message);
    }
}
