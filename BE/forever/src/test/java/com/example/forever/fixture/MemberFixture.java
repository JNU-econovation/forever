package com.example.forever.fixture;

import com.example.forever.domain.Member;
import com.example.forever.dto.member.SignUpRequest;

import java.util.List;

/**
 * 테스트용 Member 관련 픽스처 클래스
 */
public class MemberFixture {

    public static SignUpRequest createValidSignUpRequest() {
        return new SignUpRequest(
                "김테스트",
                "컴퓨터공학과",
                "테스트대학교",
                "test@example.com",
                List.of("지인추천", "검색"),
                true
        );
    }

    public static SignUpRequest createSignUpRequestWithEmail(String email) {
        return new SignUpRequest(
                "김테스트",
                "컴퓨터공학과",
                "테스트대학교",
                email,
                List.of("지인추천"),
                true
        );
    }

    public static SignUpRequest createSignUpRequestWithInflow(List<String> inflow) {
        return new SignUpRequest(
                "김테스트",
                "컴퓨터공학과",
                "테스트대학교",
                "test@example.com",
                inflow,
                true
        );
    }

    public static SignUpRequest createSignUpRequestWithName(String name) {
        return new SignUpRequest(
                name,
                "컴퓨터공학과",
                "테스트대학교",
                "test@example.com",
                List.of("지인추천"),
                true
        );
    }

    /**
     * 마케팅 동의 관련 테스트용 SignUpRequest 생성 메서드들
     */
    public static SignUpRequest createSignUpRequestWithMarketingAgreement(boolean marketingAgreement) {
        return new SignUpRequest(
                "김테스트",
                "컴퓨터공학과",
                "테스트대학교",
                "test@example.com",
                List.of("지인추천"),
                marketingAgreement
        );
    }

    public static SignUpRequest createSignUpRequestWithEmailAndMarketingAgreement(String email, Boolean marketingAgreement) {
        return new SignUpRequest(
                "김테스트",
                "컴퓨터공학과",
                "테스트대학교",
                email,
                List.of("지인추천"),
                marketingAgreement
        );
    }

    public static SignUpRequest createSignUpRequestWithNullMarketingAgreement() {
        return new SignUpRequest(
                "김테스트",
                "컴퓨터공학과",
                "테스트대학교",
                "test@example.com",
                List.of("지인추천"),
                null
        );
    }

    public static Member createMember() {
        return Member.builder()
                .email("member@example.com")
                .nickname("테스트회원")
                .major("소프트웨어공학과")
                .school("테스트대학교")
                .inflow("지인추천,검색")
                .build();
    }

    public static Member createMemberWithEmail(String email) {
        return Member.builder()
                .email(email)
                .nickname("테스트회원")
                .major("소프트웨어공학과")
                .school("테스트대학교")
                .inflow("지인추천")
                .build();
    }

    /**
     * 마케팅 동의 관련 테스트용 Member 생성 메서드들
     */
    public static Member createMemberWithMarketingAgreement(boolean marketingAgreement) {
        Member member = Member.builder()
                .email("member@example.com")
                .nickname("테스트회원")
                .major("소프트웨어공학과")
                .school("테스트대학교")
                .inflow("지인추천,검색")
                .build();
        member.setMarketingAgreement(marketingAgreement);
        return member;
    }

    public static Member createMemberWithEmailAndMarketingAgreement(String email, boolean marketingAgreement) {
        Member member = Member.builder()
                .email(email)
                .nickname("테스트회원")
                .major("소프트웨어공학과")
                .school("테스트대학교")
                .inflow("지인추천")
                .build();
        member.setMarketingAgreement(marketingAgreement);
        return member;
    }
}
