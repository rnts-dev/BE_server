package com.bside.backendapi.global.mail;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/public")
public class MailController {

    private final MailService mailService;

    @PostMapping("/mail/verification-requests")
    public ResponseEntity<String> sendMail(@Valid @RequestBody MailDTO mailDTO) throws MessagingException {
        String authCode = mailService.sendMail(mailDTO.getMail());
        return ResponseEntity.ok().body("인증코드가 발송되었습니다. " + authCode);
    }

    @PostMapping("/mail/verifications")
    public ResponseEntity<String> verification(@Valid @RequestBody MailDTO mailDTO) {
        boolean isVerified = mailService.verifiedCode(mailDTO.getMail(), mailDTO.getAuthCode());
        return ResponseEntity.ok().body(isVerified ? "인증이 완료되었습니다." : "인증 실패했습니다.");
    }
}
