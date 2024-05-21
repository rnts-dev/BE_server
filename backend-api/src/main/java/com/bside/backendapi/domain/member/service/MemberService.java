package com.bside.backendapi.domain.member.service;

import com.bside.backendapi.domain.member.domain.Member;
import com.bside.backendapi.domain.member.domain.MemberDto;
import com.bside.backendapi.domain.member.domain.MemberRole;
import com.bside.backendapi.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public Member saveMember(MemberDto memberDto) {
        Member member = Member.builder()
                .username(memberDto.getUsername())
                .email(memberDto.getPassword())
                .password(passwordEncoder.encode(memberDto.getPassword()))
                .role(MemberRole.USER)
                .build();
        return memberRepository.save(member);
    }
}
