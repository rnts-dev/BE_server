package com.bside.backendapi.domain.member.application;

import com.bside.backendapi.domain.member.domain.Member;
import com.bside.backendapi.domain.member.repository.MemberRepository;
import com.bside.backendapi.domain.member.vo.Mail;
import com.bside.backendapi.domain.member.exception.DuplicatedPasswordException;
import com.bside.backendapi.domain.member.exception.MemberNotFoundException;
import com.bside.backendapi.domain.member.exception.VerificationFailedException;
import com.bside.backendapi.global.error.exception.ErrorCode;
import com.bside.backendapi.global.jwt.application.TokenProvider;
import com.bside.backendapi.global.jwt.error.TokenNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PasswordService {

    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public String requestPasswordReset(final boolean isVerified, final String mail) {
        if (!isVerified) throw new VerificationFailedException(ErrorCode.VERIFICATION_FAILED);
        return tokenProvider.generateTokenForMail(Mail.from(mail));
    }

    public void resetPassword(final String token, final Member resetMember) {
        if (!tokenProvider.validateToken(token)) {
            throw new TokenNotFoundException(ErrorCode.TOKEN_NOT_FOUND);
        }

        String mail = String.valueOf(tokenProvider.getMailFromToken(token).get("mail"));
        Member member = memberRepository.findByMail(Mail.from(mail))
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.USER_NOT_FOUND));

        if (passwordEncoder.matches(resetMember.getPassword().password(), member.getPassword().password()))
            throw new DuplicatedPasswordException(ErrorCode.DUPLICATED_PASSWORD);
        else
            member.resetPassword(resetMember, passwordEncoder);
    }
}
