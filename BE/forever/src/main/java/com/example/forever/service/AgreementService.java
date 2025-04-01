package com.example.forever.service;


import com.example.forever.common.annotation.MemberInfo;
import com.example.forever.common.validator.MemberValidator;
import com.example.forever.domain.Member;
import com.example.forever.dto.AgreementTermsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AgreementService {
    private final MemberValidator memberValidator;

    public void agreeToTerms(MemberInfo memberInfo) {
        Member member = memberValidator.validateAndGetById(memberInfo.getMemberId());
        member.agreeTerms();
    }

    public AgreementTermsResponse isTermsAgreed(MemberInfo memberInfo) {
        Member member = memberValidator.validateAndGetById(memberInfo.getMemberId());
        return new AgreementTermsResponse(member.getIsAgreedTerms(), member.getEffectiveDateTerms());
    }

    public void agreeToPolicy(MemberInfo memberInfo) {
        Member member = memberValidator.validateAndGetById(memberInfo.getMemberId());
        member.agreePolicy();
    }

    public AgreementTermsResponse isPolicyAgreed(MemberInfo memberInfo) {
        Member member = memberValidator.validateAndGetById(memberInfo.getMemberId());
        return new AgreementTermsResponse(member.getIsAgreedPolicy(), member.getEffectiveDatePolicy());
    }


}
