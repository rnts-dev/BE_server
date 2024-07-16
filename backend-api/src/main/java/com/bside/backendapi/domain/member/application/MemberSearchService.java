package com.bside.backendapi.domain.member.application;

import com.bside.backendapi.domain.member.domain.persist.Member;
import com.bside.backendapi.domain.member.domain.persist.MemberRepository;
import com.bside.backendapi.domain.member.domain.vo.Email;
import com.bside.backendapi.domain.member.error.DuplicatedPasswordException;
import com.bside.backendapi.domain.member.error.MemberNotFoundException;
import com.bside.backendapi.global.error.exception.ErrorCode;
import com.bside.backendapi.global.oauth.domain.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberSearchService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public String searchId(final boolean isVerified, final String mail) {
        CustomOAuth2User member = memberRepository.findByEmail(Email.from(mail)).orElse(null);
        String result = null;

        if (!isVerified) result = "인증 실패했습니다.";
        if (member == null) result = "해당 이메일로 등록된 아이디가 없습니다.";
        if (member != null && isVerified) result = "아이디는 " + member.getLoginId().loginId() + " 입니다.";

        return result;
    }

    public void updatePassword(final Member updateMember) {
        Member member = memberRepository.findMemberByEmail(updateMember.getEmail())
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.USER_NOT_FOUND));

        if (passwordEncoder.matches(updateMember.getPassword().password(), member.getPassword().password()))
            throw new DuplicatedPasswordException(ErrorCode.DUPLICATED_PASSWORD);
        else member.updatePassword(updateMember, passwordEncoder);
    }
}
