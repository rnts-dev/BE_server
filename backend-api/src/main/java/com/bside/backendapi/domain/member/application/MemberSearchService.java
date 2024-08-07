package com.bside.backendapi.domain.member.application;

import com.bside.backendapi.domain.member.domain.persist.Member;
import com.bside.backendapi.domain.member.domain.persist.MemberRepository;
import com.bside.backendapi.domain.member.domain.vo.Email;
import com.bside.backendapi.domain.member.dto.SearchIdResponse;
import com.bside.backendapi.domain.member.error.DuplicatedPasswordException;
import com.bside.backendapi.domain.member.error.LoginIdNotFoundException;
import com.bside.backendapi.domain.member.error.MemberNotFoundException;
import com.bside.backendapi.domain.member.error.VerificationFailedException;
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

    public SearchIdResponse searchId(final boolean isVerified, final String mail) {
        CustomOAuth2User member = memberRepository.findByEmail(Email.from(mail)).orElse(null);

        if (!isVerified) throw new VerificationFailedException(ErrorCode.VERIFICATION_FAILED);
        if (member == null) throw new LoginIdNotFoundException(ErrorCode.LOGINID_NOT_FOUND);

        return SearchIdResponse.of(member.getLoginId());
    }

    public void updatePassword(final Member updateMember) {
        Member member = memberRepository.findMemberByEmail(updateMember.getEmail())
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.USER_NOT_FOUND));

        if (passwordEncoder.matches(updateMember.getPassword().password(), member.getPassword().password()))
            throw new DuplicatedPasswordException(ErrorCode.DUPLICATED_PASSWORD);
        else member.updatePassword(updateMember, passwordEncoder);
    }
}
