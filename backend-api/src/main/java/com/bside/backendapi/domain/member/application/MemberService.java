package com.bside.backendapi.domain.member.application;

import com.bside.backendapi.domain.member.domain.persist.Member;
import com.bside.backendapi.domain.member.domain.persist.MemberRepository;
import com.bside.backendapi.domain.member.domain.vo.Email;
import com.bside.backendapi.domain.member.domain.vo.Nickname;
import com.bside.backendapi.domain.member.dto.MemberResponse;
import com.bside.backendapi.domain.member.error.DuplicatedEmailException;
import com.bside.backendapi.domain.member.error.DuplicatedNicknameException;
import com.bside.backendapi.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // 계정 생성
    public MemberResponse join(final Member member) {
        existedEmail(member.getEmail());
        existedNickname(member.getNickname());

        Member savedMember = memberRepository.save(member.encode(passwordEncoder));
        return MemberResponse.of(savedMember);
    }

    // 이메일 중복 체크
    private void existedEmail(final Email email) {
        if (memberRepository.existsByEmail(email)) {
            throw new DuplicatedEmailException(ErrorCode.DUPLICATED_EMAIL);
        }
    }

    // 닉네임 중복 체크
    private void existedNickname(final Nickname nickname) {
        if (memberRepository.existsByNickname(nickname)) {
            throw new DuplicatedNicknameException(ErrorCode.DUPLICATED_NICKNAME);
        }
    }
}
