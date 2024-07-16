package com.bside.backendapi.global.mail;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Mail", description = "Mail API Document")
public class MailController {

    private final MailService mailService;

    @Operation(summary = "메일을 통해 인증코드 전송", description = "사용자가 입력한 메일(mail)을 통해 인증코드를 전송합니다.")
    @PostMapping("/mail/verification-requests")
    public ResponseEntity<String> sendMail(@Valid @RequestBody MailDTO mailDTO) throws MessagingException {
        String authCode = mailService.sendMail(mailDTO.getMail());
        return ResponseEntity.ok().body("인증코드가 발송되었습니다. " + authCode);
    }

    @Operation(summary = "인증코드 검증", description = "사용자가 입력했던 메일(mail)과 인증코드(authCode)를 통해 인증코드를 검증합니다.")
    @PostMapping("/mail/verifications")
    public ResponseEntity<String> verification(@Valid @RequestBody MailDTO mailDTO) {
        boolean isVerified = mailService.verifiedCode(mailDTO.getMail(), mailDTO.getAuthCode());
        return ResponseEntity.ok().body(isVerified ? "인증이 완료되었습니다." : "인증 실패했습니다.");
    }
}
