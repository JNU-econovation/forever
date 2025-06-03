//package com.example.forever.email;
//
//import com.example.forever.domain.VerificationCode;
//import jakarta.mail.MessagingException;
//import java.time.LocalDateTime;
//import java.util.concurrent.ThreadLocalRandom;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Slf4j
//@Transactional
//@Service
//@RequiredArgsConstructor
//public class EmailSendService {
//
//    private final EmailService emailService;
//    private final VerificationCodeRepository verificationCodeRepository;
//
//    public void sendCodeToEmail(String email) {
//        VerificationCode createdCode = createVerificationCode(email);
//        String title = "Img Forest 이메일 인증 번호";
//
//        String content = "<html>"
//                + "<body>"
//                + "<h1>요약쏙 인증 코드: " + createdCode.getCode() + "</h1>"
//                + "<p>해당 코드를 앱에 입력하세요.</p>"
//                + "<footer style='color: grey; font-size: small;'>"
//                + "<p>※본 메일은 자동응답 메일이므로 본 메일에 회신하지 마시기 바랍니다.</p>"
//                + "</footer>"
//                + "</body>"
//                + "</html>";
//        try {
//            emailService.sendEmail(email, title, content);
//        } catch (RuntimeException | MessagingException e) {
//            e.printStackTrace(); // 또는 로거를 사용하여 상세한 예외 정보 로깅
//            throw new RuntimeException("Unable to send email in sendCodeToEmail", e); // 원인 예외를 포함시키기
//        }
//    }
//
//    // 인증 코드 생성 및 저장
//    public VerificationCode createVerificationCode(String email) {
//        String randomCode = generateRandomCode(6);
//        VerificationCode code = VerificationCode.builder()
//                .email(email)
//                .code(randomCode) // 랜덤 코드 생성
//                .expiresTime(LocalDateTime.now().plusDays(1)) // 1일 후 만료
//                .build();
//
//        return verificationCodeRepository.save(code);
//    }
//
//    public String generateRandomCode(int length) {
//        // 숫자 + 대문자 + 소문자
//        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
//        StringBuilder sb = new StringBuilder();
//        ThreadLocalRandom random = ThreadLocalRandom.current();
//
//        for (int i = 0; i < length; i++) {
//            int index = random.nextInt(characters.length());
//            sb.append(characters.charAt(index));
//        }
//
//        return sb.toString();
//    }
//
//    // 인증 코드 유효성 검사
//    public void verifyCode(String email, String code) {
//         verificationCodeRepository.findByEmailAndCode(email, code)
//                .map(vc -> vc.getExpiresTime().isAfter(LocalDateTime.now()))
//                .orElseThrow(() -> new IllegalArgumentException("인증 코드가 유효하지 않습니다."));
//    }
//
//    @Transactional
//    @Scheduled(cron = "0 0 12 * * ?") // 매일 정오에 해당 만료 코드 삭제
//    public void deleteExpiredVerificationCodes() {
//        verificationCodeRepository.deleteByExpiresTimeBefore(LocalDateTime.now());
//    }
//}
