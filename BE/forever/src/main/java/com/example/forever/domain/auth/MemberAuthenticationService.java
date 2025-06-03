package com.example.forever.domain.auth;

import com.example.forever.domain.Member;
import com.example.forever.domain.member.Email;
import com.example.forever.domain.member.MemberDomainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberAuthenticationService {
    
    private final MemberDomainRepository memberRepository;
    
    public AuthenticationResult authenticateMember(Email email, KakaoAccessToken kakaoAccessToken) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        
        if (optionalMember.isEmpty()) {
            // 가입이 안 된 경우: 회원가입 필요
            return AuthenticationResult.signupRequired(email);
        }
        
        Member member = optionalMember.get();
        
        if (member.isDeleted()) {
            // 탈퇴한 회원인 경우
            return AuthenticationResult.deletedMember();
        }
        
        // 기존 회원 로그인 성공
        return AuthenticationResult.loginSuccess(member, kakaoAccessToken);
    }
}
