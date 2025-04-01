package com.example.forever.service;


import com.example.forever.common.annotation.MemberInfo;
import com.example.forever.common.validator.MemberValidator;
import com.example.forever.domain.Member;
import com.example.forever.dto.AiTokenUsageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AiUsageCheckService {

    private final MemberValidator memberValidator;

    public AiTokenUsageResponse checkTokenUsage(MemberInfo memberInfo) {
        //만약에 member의 availableTokens이 1이상이라면 true 반환
        //아니라면 false 반환
        Member member = memberValidator.validateAndGetById(memberInfo.getMemberId());
        return new AiTokenUsageResponse(member.isAvailableTokens());
    }

    public void checkCompleteUpload(MemberInfo memberInfo) {
        //member의 availableTokens을 1 감소시킨다.
        Member member = memberValidator.validateAndGetById(memberInfo.getMemberId());
        if (!member.isAvailableTokens()) {
            throw new RuntimeException("No available tokens");
        }
        member.useToken();
    }
}
