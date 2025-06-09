package com.example.forever.controller;

import com.example.forever.application.member.MarketingAgreementApplicationService;
import com.example.forever.common.test.TestMemberArgumentResolver;
import com.example.forever.dto.member.MarketingAgreementResponse;
import com.example.forever.dto.member.MarketingAgreementUpdateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("마케팅 동의 API 컨트롤러 테스트")
class MarketingAgreementControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private MarketingAgreementApplicationService marketingAgreementApplicationService;

    @InjectMocks
    private AgreementController agreementController;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(agreementController)
                .setCustomArgumentResolvers(new TestMemberArgumentResolver())
                .build();
    }

    @Test
    @DisplayName("마케팅 동의 상태 조회 API 테스트 - 성공")
    void getMarketingAgreement_Success() throws Exception {
        // given
        Long memberId = 1L;
        MarketingAgreementResponse response = new MarketingAgreementResponse(true, "25-06-08");
        
        when(marketingAgreementApplicationService.getMarketingAgreement(memberId))
                .thenReturn(response);

        // when & then
        mockMvc.perform(get("/api/agreement/marketing")
                        .header("Authorization", "Bearer test-token"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.data.is_agreement").value(true))
                .andExpect(jsonPath("$.data.effective_date").value("25-06-08"));

        verify(marketingAgreementApplicationService).getMarketingAgreement(memberId);
    }

    @Test
    @DisplayName("마케팅 동의 상태 변경 API 테스트 - 동의함으로 변경")
    void updateMarketingAgreement_ToAgreed_Success() throws Exception {
        // given
        Long memberId = 1L;
        MarketingAgreementUpdateRequest request = new MarketingAgreementUpdateRequest(true);
        MarketingAgreementResponse response = new MarketingAgreementResponse(true, "25-06-08");
        
        when(marketingAgreementApplicationService.updateMarketingAgreement(eq(memberId), any()))
                .thenReturn(response);

        // when & then
        mockMvc.perform(patch("/api/agreement/marketing")
                        .header("Authorization", "Bearer test-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.data.is_agreement").value(true))
                .andExpect(jsonPath("$.data.effective_date").value("25-06-08"));

        verify(marketingAgreementApplicationService).updateMarketingAgreement(eq(memberId), any(MarketingAgreementUpdateRequest.class));
    }

    @Test
    @DisplayName("마케팅 동의 상태 변경 API 테스트 - 동의하지 않음으로 변경")
    void updateMarketingAgreement_ToNotAgreed_Success() throws Exception {
        // given
        Long memberId = 1L;
        MarketingAgreementUpdateRequest request = new MarketingAgreementUpdateRequest(false);
        MarketingAgreementResponse response = new MarketingAgreementResponse(false, "25-06-08");
        
        when(marketingAgreementApplicationService.updateMarketingAgreement(eq(memberId), any()))
                .thenReturn(response);

        // when & then
        mockMvc.perform(patch("/api/agreement/marketing")
                        .header("Authorization", "Bearer test-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.is_agreement").value(false));
    }

    @Test
    @DisplayName("마케팅 동의 상태 변경 API 테스트 - 잘못된 요청 데이터")
    void updateMarketingAgreement_InvalidRequest() throws Exception {
        // given
        String invalidJson = "{ \"is_agreement\": null }";

        // when & then
        mockMvc.perform(patch("/api/agreement/marketing")
                        .header("Authorization", "Bearer test-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }
}
