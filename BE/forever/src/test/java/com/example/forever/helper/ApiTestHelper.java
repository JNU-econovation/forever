package com.example.forever.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * API 테스트를 위한 헬퍼 클래스
 */
public class ApiTestHelper {

    public static ResultActions performSignUpRequest(MockMvc mockMvc, ObjectMapper objectMapper, Object request) throws Exception {
        return mockMvc.perform(post("/api/oauth/kakao/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
    }

}
