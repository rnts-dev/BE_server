package com.bside.backendapi.global.mail;

import com.bside.backendapi.global.redis.RedisService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private final RedisService redisService;
    private static final String senderEmail = "seyoungkwon29@naver.com";
    private static final String AUTH_CODE_PREFIX = "AuthCode";

    private String createCode() {
        Random random = new Random();
        int randomCode = 100000 + random.nextInt(900000);
        return String.valueOf(randomCode);
    }

    // 이메일 폼 생성
    private MimeMessage createMail(final String email, final String authCode) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject("출발했어?! 인증번호입니다.");
        message.setFrom(senderEmail);
        message.setText(setMessage(authCode), "utf-8", "html");

        return message;
    }

    private String setMessage(final String authCode) {
        String message = "";
        message += "<h3>요청하신 인증 번호입니다.</h3>";
        message += "<h1>" + authCode + "</h1>";
        return message;
    }

    // 인증코드 메일 전송
    public String sendMail(final String to) throws MessagingException {
        String authCode = createCode();
        MimeMessage message = createMail(to, authCode);

        // 이메일 발송
        javaMailSender.send(message);

        // 인증코드 redis에 저장
        redisService.setValues(AUTH_CODE_PREFIX + to, authCode);

        return authCode;
    }

    // 인증코드 확인
    public boolean verifiedCode(final String mail, final String authCode) {
        String redisAuthCode = redisService.getValues(AUTH_CODE_PREFIX + mail);

        return redisService.checkExistsValue(redisAuthCode) && redisAuthCode.equals(authCode);
    }
}
