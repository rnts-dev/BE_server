package com.bside.backendapi.global.mail;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MailController {

    private final MailService mailService;

    @PostMapping("/mail/verification-requests")
    public ResponseEntity<String> sendMail(@RequestBody MailDto mailDto) throws MessagingException {
        String authCode = mailService.sendMail(mailDto.getMail());
        return ResponseEntity.ok().body("인증코드가 발송되었습니다. " + authCode);
    }

    @PostMapping("/mail/verifications")
    public ResponseEntity<String> verification(@Valid @RequestBody MailDto mailDto) {
        boolean isVerified = mailService.verifiedCode(mailDto.getMail(), mailDto.getAuthCode());
        return ResponseEntity.ok().body(isVerified ? "인증이 완료되었습니다." : "인증 실패했습니다.");
    }
}
