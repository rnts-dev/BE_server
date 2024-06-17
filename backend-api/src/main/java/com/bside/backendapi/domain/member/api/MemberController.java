package com.bside.backendapi.domain.member.api;

import com.bside.backendapi.domain.member.application.MemberReadService;
import com.bside.backendapi.domain.member.application.MemberService;
import com.bside.backendapi.domain.member.dto.JoinRequest;
import com.bside.backendapi.domain.member.dto.MemberDetailsResponse;
import com.bside.backendapi.domain.member.dto.MemberResponse;
import com.bside.backendapi.domain.member.dto.MemberUpdateRequest;
import com.bside.backendapi.global.security.principal.CustomUserDetails;
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
public class MemberController {

    private final MemberService memberService;
    private final MemberReadService memberReadService;

    // join
    @PostMapping("/public/members")
    public ResponseEntity<MemberResponse> join(@Valid @RequestBody JoinRequest joinRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.join(joinRequest.toEntity()));
    }

    // read
    @GetMapping("/members/detail")
    public ResponseEntity<MemberDetailsResponse> getDetailById() {
        return ResponseEntity.ok().body(memberReadService.getDetailBy(this.getPrincipal()));
    }

    // update
    @PatchMapping("/members")
    public ResponseEntity<Void> update(@Valid @RequestBody MemberUpdateRequest memberUpdateRequest) {
        memberService.update(memberUpdateRequest.toEntity(), this.getPrincipal().getId());
        return ResponseEntity.ok().build();
    }

    // delete
    @DeleteMapping("/members")
    public ResponseEntity<Void> delete() {
        memberService.delete(this.getPrincipal().getId());
        return ResponseEntity.noContent().build();
    }

    // 현재 인증된 사용자의 정보 가져오기
    public CustomUserDetails getPrincipal() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof CustomUserDetails) {
            return (CustomUserDetails) principal;
        } else {
            throw new IllegalStateException("현재 인증된 사용자가 없습니다.");
        }
    }

}
