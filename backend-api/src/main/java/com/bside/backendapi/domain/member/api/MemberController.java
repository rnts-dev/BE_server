package com.bside.backendapi.domain.member.api;

import com.bside.backendapi.domain.member.application.MemberReadService;
import com.bside.backendapi.domain.member.application.MemberSearchService;
import com.bside.backendapi.domain.member.application.MemberService;
import com.bside.backendapi.domain.member.dto.*;
import com.bside.backendapi.global.mail.MailService;
import com.bside.backendapi.global.mail.VerifiedRequest;
import com.bside.backendapi.global.oauth.domain.CustomOAuth2User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Member", description = "Member API Document")
public class MemberController {

    private final MemberService memberService;
    private final MemberReadService memberReadService;
    private final MemberSearchService memberSearchService;
    private final MailService mailService;

    @Operation(summary = "회원가입", description = "사용자가 입력한 정보를 토대로 회원가입을 진행합니다.")
    @PostMapping("/public/members")
    public ResponseEntity<MemberResponse> join(@Valid @RequestBody JoinRequest joinRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.join(joinRequest.toEntity()));
    }

    @Operation(summary = "아이디 중복 확인", description = "회원가입 시 아이디 중복 여부를 확인합니다.")
    @PostMapping("/public/existedLoginId")
    public ResponseEntity<Void> existedLoginId(@Valid @RequestBody LoginIdRequest loginIdRequest) {
        memberService.existedLoginId(loginIdRequest.getLoginId());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "사용자 정보 조회", description = "사용자 정보를 조회합니다.")
    @GetMapping("/members/detail")
    public ResponseEntity<MemberDetailsResponse> getDetailById() {
        return ResponseEntity.ok().body(memberReadService.getDetailBy(this.getPrincipal()));
    }

    @Operation(summary = "사용자 정보 수정", description = "사용자 정보를 수정합니다.")
    @PatchMapping("/members")
    public ResponseEntity<Void> update(@Valid @RequestBody MemberUpdateRequest memberUpdateRequest) {
        memberService.update(memberUpdateRequest.toEntity(), this.getPrincipal().getId());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "사용자 삭제", description = "해당 사용자의 계정을 정지합니다. DB에는 유지하게 됩니다.")
    @DeleteMapping("/members")
    public ResponseEntity<Void> delete() {
        memberService.delete(this.getPrincipal().getId());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "사용자 성향 선택", description = "사용자의 성향을 수정합니다.")
    @PatchMapping("/members/tendency")
    public ResponseEntity<Void> updateTendency(@Valid @RequestBody TendencyRequest tendencyRequest) {
        memberService.updateTendency(tendencyRequest.toEntity(), this.getPrincipal().getId());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "아이디 찾기", description = "인증코드와 이메일로 회원 검증 후 해당 메일을 가진 회원의 ID를 표시하게 됩니다.")
    @PostMapping("/public/searchId")
    public ResponseEntity<String> searchId(@Valid @RequestBody VerifiedRequest verifiedRequest) {
        boolean isVerified = mailService.verifiedCode(verifiedRequest.getMail(), verifiedRequest.getAuthCode());
        String result = memberSearchService.searchId(isVerified, verifiedRequest.getMail());
        return ResponseEntity.ok().body(result);
    }

    @Operation(summary = "새 비밀번호 변경",
            description = "인증코드 발송 시 사용된 메일(mail)과 새 비밀번호(newPassword), 비밀번호 확인(confirmPassword)를 통해 새 비밀번호로 변경합니다.")
    @PatchMapping("/public/updatePassword")
    public ResponseEntity<String> updatePassword(@Valid @RequestBody MemberUpdatePasswordRequest memberUpdatePasswordRequest) {
        if (!memberUpdatePasswordRequest.getNewPassword().equals(memberUpdatePasswordRequest.getConfirmPassword()))
            return ResponseEntity.badRequest().body("비밀번호를 올바르게 입력하세요.");

        memberSearchService.updatePassword(memberUpdatePasswordRequest.toEntity());
        return ResponseEntity.ok().body("비밀번호가 성공적으로 변경되었습니다.");
    }

    // 현재 인증된 사용자의 정보 가져오기
    public CustomOAuth2User getPrincipal() {
        return (CustomOAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
