package com.example.forever.domain.auth;

import com.example.forever.domain.Member;
import com.example.forever.domain.member.Email;

public class AuthenticationResult {
    
    public enum Status {
        LOGIN_SUCCESS,      // 기존 회원 로그인 성공
        SIGNUP_REQUIRED,    // 회원가입 필요
        DELETED_MEMBER      // 탈퇴한 회원
    }
    
    private final Status status;
    private final Member member;
    private final Email email;
    private final KakaoAccessToken kakaoAccessToken;
    
    private AuthenticationResult(Status status, Member member, Email email, KakaoAccessToken kakaoAccessToken) {
        this.status = status;
        this.member = member;
        this.email = email;
        this.kakaoAccessToken = kakaoAccessToken;
    }
    
    public static AuthenticationResult loginSuccess(Member member, KakaoAccessToken kakaoAccessToken) {
        return new AuthenticationResult(Status.LOGIN_SUCCESS, member, null, kakaoAccessToken);
    }
    
    public static AuthenticationResult signupRequired(Email email) {
        return new AuthenticationResult(Status.SIGNUP_REQUIRED, null, email, null);
    }
    
    public static AuthenticationResult deletedMember() {
        return new AuthenticationResult(Status.DELETED_MEMBER, null, null, null);
    }
    
    public Status getStatus() {
        return status;
    }
    
    public Member getMember() {
        return member;
    }
    
    public Email getEmail() {
        return email;
    }
    
    public KakaoAccessToken getKakaoAccessToken() {
        return kakaoAccessToken;
    }
    
    public boolean isLoginSuccess() {
        return status == Status.LOGIN_SUCCESS;
    }
    
    public boolean isSignupRequired() {
        return status == Status.SIGNUP_REQUIRED;
    }
    
    public boolean isDeletedMember() {
        return status == Status.DELETED_MEMBER;
    }
}
