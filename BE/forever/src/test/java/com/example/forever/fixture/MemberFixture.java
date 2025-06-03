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
                List.of("지인추천", "검색")
        );
    }

    public static SignUpRequest createSignUpRequestWithEmail(String email) {
        return new SignUpRequest(
                "김테스트",
                "컴퓨터공학과",
                "테스트대학교",
                email,
                List.of("지인추천")
        );
    }

    public static SignUpRequest createSignUpRequestWithInflow(List<String> inflow) {
        return new SignUpRequest(
                "김테스트",
                "컴퓨터공학과",
                "테스트대학교",
                "test@example.com",
                inflow
        );
    }

    public static SignUpRequest createSignUpRequestWithName(String name) {
        return new SignUpRequest(
                name,
                "컴퓨터공학과",
                "테스트대학교",
                "test@example.com",
                List.of("지인추천")
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
}
