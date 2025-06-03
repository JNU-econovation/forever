package com.example.forever.domain.member;

import com.example.forever.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberRegistrationService {
    
    private final EmailDuplicationChecker emailDuplicationChecker;
    
    public Member registerNewMember(
            Email email,
            Nickname nickname, 
            School school,
            Major major,
            List<String> inflow) {
        
        //  이메일 중복 검사
        emailDuplicationChecker.checkEmailNotDuplicated(email);
        
        // inflow 변환
        String inflowString = convertInflowToString(inflow);
        
        // Member 도메인 객체 생성
        return Member.builder()
                .email(email.getValue())
                .nickname(nickname.getValue())
                .school(school.getValue())
                .major(major.getValue())
                .inflow(inflowString)
                .build();
    }
    
    private String convertInflowToString(List<String> inflow) {
        return inflow != null ? String.join(",", inflow) : null;
    }
}
