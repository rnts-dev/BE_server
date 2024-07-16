package com.bside.backendapi.domain.member.api;

import com.bside.backendapi.domain.member.application.MemberSearchService;
import com.bside.backendapi.domain.member.domain.vo.Password;
import com.bside.backendapi.domain.member.dto.MemberUpdatePasswordRequest;
import com.bside.backendapi.global.mail.MailDTO;
import com.bside.backendapi.global.mail.MailService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/public")
public class MemberSearchController {

    private final MailService mailService;
    private final MemberSearchService memberSearchService;

    @PostMapping("/searchId")
    @Operation(summary = "아이디 찾기", description = "인증코드와 이메일로 회원 검증 후 아이디 표시")
    public ResponseEntity<String> searchId(@Valid @RequestBody MailDTO mailDTO) {
        boolean isVerified = mailService.verifiedCode(mailDTO.getMail(), mailDTO.getAuthCode());
        String result = memberSearchService.searchId(isVerified, mailDTO.getMail());
        return ResponseEntity.ok().body(result);
    }

    @PatchMapping("/updatePassword")
    @Operation(summary = "새 비밀번호 변경", description = "인증코드 검증 후 새 비밀번호로 변경")
    public ResponseEntity<String> updatePassword(@Valid @RequestBody MemberUpdatePasswordRequest memberUpdatePasswordRequest) {
        Password password = memberUpdatePasswordRequest.getPassword();
        Password checkPassword = memberUpdatePasswordRequest.getCheckPassword();

        if (!password.equals(checkPassword)) return ResponseEntity.badRequest().body("비밀번호를 올바르게 입력하세요.");

        memberSearchService.updatePassword(memberUpdatePasswordRequest.toEntity());
        return ResponseEntity.ok().body("비밀번호가 성공적으로 변경되었습니다.");
    }

}
