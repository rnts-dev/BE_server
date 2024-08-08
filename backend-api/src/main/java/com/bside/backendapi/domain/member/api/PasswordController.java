package com.bside.backendapi.domain.member.api;

import com.bside.backendapi.domain.member.application.PasswordService;
import com.bside.backendapi.domain.member.dto.TokenWithPasswordRequest;
import com.bside.backendapi.global.mail.MailService;
import com.bside.backendapi.global.mail.VerifiedRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Password", description = "Password API Document")
public class PasswordController {

    private final PasswordService passwordService;
    private final MailService mailService;

    @Operation(summary = "인증코드 검증 및 JWT 토큰 생성",
            description = "인증코드 검증 후 JWT 토큰을 생성하고 클라이언트에 전달합니다.")
    @PostMapping("/public/requestPasswordReset")
    public ResponseEntity<Map<String, String>> requestPasswordReset(@Valid @RequestBody VerifiedRequest verifiedRequest) {
        boolean isVerified = mailService.verifiedCode(verifiedRequest.getMail(), verifiedRequest.getAuthCode());
        String token = passwordService.requestPasswordReset(isVerified, verifiedRequest.getMail());

        Map<String, String> response = new HashMap<>();
        response.put("token", token);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "비밀번호 변경", description = "JWT 토큰을 검증하고 비밀번호를 변경합니다.")
    @PatchMapping("/public/resetPassword")
    public ResponseEntity<Map<String, String>> resetPassword(@Valid @RequestBody TokenWithPasswordRequest tokenWithPasswordRequest) {
        passwordService.resetPassword(tokenWithPasswordRequest.getToken(), tokenWithPasswordRequest.toEntity());

        Map<String, String> response = new HashMap<>();
        response.put("result", "비밀번호가 성공적으로 변경되었습니다.");

        return ResponseEntity.ok(response);
    }
}
