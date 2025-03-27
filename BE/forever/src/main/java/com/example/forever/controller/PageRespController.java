package com.example.forever.controller;

import com.example.forever.common.annotation.AuthMember;
import com.example.forever.common.annotation.MemberInfo;
import com.example.forever.common.response.ApiResponse;
import com.example.forever.common.response.ApiResponseGenerator;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PageRespController {

    @GetMapping(path = "/oauth" , produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> responseHtmlPage() {
        try {
            ClassPathResource htmlFile = new ClassPathResource("html/index.html");
            String htmlContent = new String(htmlFile.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(htmlContent);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("<html><body><h1>Error loading page</h1></body></html>");
        }
    }

    @GetMapping("/test")
    public ApiResponse<?> test(@AuthMember MemberInfo memberInfo) {
        System.out.println("멤버아이디: " + memberInfo.getMemberId());
        return ApiResponseGenerator.success(HttpStatus.OK);
    }
}

