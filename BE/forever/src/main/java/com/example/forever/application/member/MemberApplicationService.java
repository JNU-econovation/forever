package com.example.forever.application.member;

import com.example.forever.domain.Member;
import com.example.forever.domain.member.*;
import com.example.forever.infrastructure.auth.TokenService;
import com.example.forever.infrastructure.auth.AuthenticationResponseBuilder;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberApplicationService {
    
    private final MemberRegistrationService memberRegistrationService;
    private final MemberDomainRepository memberRepository;
    private final TokenService tokenService;
    private final AuthenticationResponseBuilder authResponseBuilder;
    
    public void signUp(SignUpCommand command, HttpServletResponse response) {
        // 도메인 값 객체 생성
        Email email = new Email(command.email());
        Nickname nickname = new Nickname(command.name());
        School school = new School(command.school());
        Major major = new Major(command.major());
        
        // 도메인 서비스를 통한 회원 등록
        Member newMember = memberRegistrationService.registerNewMember(
                email, nickname, school, major, command.inflow()
        );
        
        // 마케팅 동의 설정
        if (command.marketingAgreement() != null) {
            newMember.setMarketingAgreement(command.marketingAgreement());
        }
        
        // 회원 저장
        Member savedMember = memberRepository.save(newMember);
        
        // 토큰 생성 및 응답 설정
        tokenService.generateAndSetTokens(savedMember, response);
    }
}
