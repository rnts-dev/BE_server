package com.bside.backendapi.domain.member.application;

import com.bside.backendapi.domain.member.domain.persist.Member;
import com.bside.backendapi.domain.member.domain.persist.MemberRepository;
import com.bside.backendapi.domain.member.domain.vo.Email;
import com.bside.backendapi.domain.member.domain.vo.LoginId;
import com.bside.backendapi.domain.member.domain.vo.Nickname;
import com.bside.backendapi.domain.member.dto.MemberResponse;
import com.bside.backendapi.domain.member.error.*;
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

    public MemberResponse join(final Member member) {
        existedEmail(member.getEmail());
        existedNickname(member.getNickname());

        Member savedMember = memberRepository.save(member.encode(passwordEncoder));
        return MemberResponse.of(savedMember);
    }

    public void existedLoginId(final LoginId loginId) {
        if (memberRepository.existsByLoginId(loginId)) {
            throw new DuplicatedLoginIdException(ErrorCode.DUPLICATED_LOGINID);
        }
    }

    public void update(final Member updateMember, final Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.USER_NOT_FOUND));
        member.update(updateMember, passwordEncoder);
    }

    public void delete(final Long memberId) {
        memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.USER_NOT_FOUND))
                .delete();
    }

    public void existedEmail(final Email email) {
        if (memberRepository.existsByEmail(email)) {
            throw new DuplicatedEmailException(ErrorCode.DUPLICATED_EMAIL);
        }
    }

    public void mailNotFound(final Email email) {
        if (!memberRepository.existsByEmail(email)) {
            throw new EmailNotFoundException(ErrorCode.EMAIL_NOT_FOUND);
        }
    }

    private void existedNickname(final Nickname nickname) {
        if (memberRepository.existsByNickname(nickname)) {
            throw new DuplicatedNicknameException(ErrorCode.DUPLICATED_NICKNAME);
        }
    }

    public void updateTendency(final Member updateMember, final Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.USER_NOT_FOUND));

        member.setTendency(updateMember);
    }
}
