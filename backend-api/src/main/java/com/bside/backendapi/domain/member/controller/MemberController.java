package com.bside.backendapi.domain.member.controller;

import com.bside.backendapi.domain.member.domain.Member;
import com.bside.backendapi.domain.member.domain.MemberDto;
import com.bside.backendapi.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;;

    @PostMapping("/signUp")
    public Map<String, String> signUp(@RequestBody MemberDto memberDto) {
        log.info("--------------------------- MemberController ---------------------------");
        log.info("memberDto = {}", memberDto);
        Map<String, String> response = new HashMap<>();
        Optional<Member> byEmail = memberService.findByEmail(memberDto.getEmail());
        if (byEmail.isPresent()) {
            response.put("error", "이미 존재하는 이메일입니다");
        } else {
            memberService.saveMember(memberDto);
            response.put("success", "성공적으로 처리하였습니다");
        }
        return response;
    }
}
