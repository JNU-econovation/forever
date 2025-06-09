package com.example.forever.application.member;

import com.example.forever.domain.member.MarketingAgreement;
import com.example.forever.domain.member.MarketingAgreementService;
import com.example.forever.domain.member.MemberId;
import com.example.forever.dto.member.MarketingAgreementResponse;
import com.example.forever.dto.member.MarketingAgreementUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;

/**
 * 마케팅 동의 관련 애플리케이션 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MarketingAgreementApplicationService {
    
    private final MarketingAgreementService marketingAgreementService;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yy-MM-dd");
    
    /**
     * 마케팅 동의 정보 조회
     */
    public MarketingAgreementResponse getMarketingAgreement(Long memberId) {
        MemberId memberIdVO = MemberId.of(memberId);
        MarketingAgreement agreement = marketingAgreementService.getMarketingAgreement(memberIdVO);
        
        return new MarketingAgreementResponse(
                agreement.isAgreed(),
                agreement.getAgreementDate().format(DATE_FORMATTER)
        );
    }
    
    /**
     * 마케팅 동의 상태 변경
     */
    @Transactional
    public MarketingAgreementResponse updateMarketingAgreement(Long memberId, 
                                                               MarketingAgreementUpdateRequest request) {
        MemberId memberIdVO = MemberId.of(memberId);
        MarketingAgreement updatedAgreement = marketingAgreementService.updateMarketingAgreement(
                memberIdVO, 
                request.isAgreement()
        );
        
        return new MarketingAgreementResponse(
                updatedAgreement.isAgreed(),
                updatedAgreement.getAgreementDate().format(DATE_FORMATTER)
        );
    }
}
