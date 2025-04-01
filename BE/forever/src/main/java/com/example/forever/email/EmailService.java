package com.example.forever.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;

    public void sendEmail(String toEmail,String title, String content) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(toEmail);
        helper.setSubject(title);
        helper.setText(content, true); // true를 설정해서 HTML을 사용 가능하게 함
        helper.setReplyTo("4everr.2o25@gmail.com"); // 회신 불가능한 주소 설정
        try {
            emailSender.send(message);
        } catch (RuntimeException e) {
            e.printStackTrace(); // 또는 로거를 사용하여 상세한 예외 정보 로깅
            throw new RuntimeException("Unable to send email in sendEmail", e); // 원인 예외를 포함시키기
        }
    }

    public SimpleMailMessage createEmailForm(String toEmail, String title, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(title);
        message.setText(text);
        return message;
    }


}